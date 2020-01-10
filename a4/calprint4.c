#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>
#define MAX_LINE_LEN 80

#include "emalloc.h"
#include "ics.h"
#include "listy.h"



int repeat_event = 0;

void strip(char *dt){
    
    if (dt[0]=='0'){
        dt[0] =' ';
    }
}

void lowercase(char *degit){
    
    for(int i = 0; degit[i]; i++){
        degit[i] = tolower(degit[i]);
    }
}


void date_output (event_t *event,char *cal_day,char *dtstart,char *dtend,char *start_degit, char *end_degit){
    
    strftime(cal_day,MAX_LINE_LEN, "%B %d, %Y (%a)",localtime(&event->dt_time));
    strftime(start_degit,MAX_LINE_LEN, "%p",localtime(&event->dtstart));
    strftime(dtstart,MAX_LINE_LEN, "%I:%M ",localtime(&event->dtstart));
    strip(dtstart);
    lowercase(start_degit);
    strcat(dtstart,start_degit);
    strftime(end_degit,MAX_LINE_LEN, "%p",localtime(&event->dtend));
    strftime(dtend,MAX_LINE_LEN,"%I:%M ",localtime(&event->dtend));
    strip(dtend);
    lowercase(end_degit);
    strcat(dtend,end_degit);
    
}

void print_event(node_t *list, time_t start, time_t end) {
    
    assert(list != NULL);
    
    char cal_day [MAX_LINE_LEN];
    char dtstart [MAX_LINE_LEN] ;
    char dtend [MAX_LINE_LEN];
    char start_degit [MAX_LINE_LEN];
    char end_degit [MAX_LINE_LEN];
    
    
    event_t *temp_event = list->val;
    
    int index = 0;
    
    if (start <= temp_event->dt_time && end >= temp_event->dt_time){
        
        date_output(temp_event,cal_day,dtstart,dtend,start_degit,end_degit);
        printf("%s\n",cal_day);
        int k;
        for (k=0; k < strlen(cal_day); k++){
            printf("-");
        }
        
        printf("\n");
        index++;
        memset(cal_day,0,MAX_LINE_LEN);
        memset(dtstart,0,MAX_LINE_LEN);
        memset(dtend,0,MAX_LINE_LEN);
        memset(start_degit,0,MAX_LINE_LEN);
        memset(end_degit,0,MAX_LINE_LEN);
    }
    
    
    for (; list != NULL; list = list->next){
        event_t *event = list->val;
        if (start <= event->dt_time && end >= event->dt_time){
            date_output(event,cal_day,dtstart,dtend,start_degit,end_degit);
            if (event->dt_time != temp_event->dt_time){
                if (index > 0){
                    printf("\n");
                }
                printf("%s\n",cal_day);
                int n;
                for (n=0; n < strlen(cal_day); n++){
                    printf("-");
                }
                printf("\n");
                index++;
                printf("%s to %s: %s [%s]\n", dtstart, dtend, event->summary , event->location);
                *temp_event=*event;
            }else{
                printf("%s to %s: %s [%s]\n", dtstart, dtend, event->summary , event->location);
            }
        }
        memset(cal_day,0,MAX_LINE_LEN);
        memset(dtstart,0,MAX_LINE_LEN);
        memset(dtend,0,MAX_LINE_LEN);
        memset(start_degit,0,MAX_LINE_LEN);
        memset(end_degit,0,MAX_LINE_LEN);
    }
    
}


time_t extract_dt_time(char *input){
    struct tm temp_time;
    time_t    full_time;
    
    memset(&temp_time, 0, sizeof(struct tm));
    sscanf(input, "%4d%2d%2dT%2d%2d%2d",
           &temp_time.tm_year, &temp_time.tm_mon, &temp_time.tm_mday, &temp_time.tm_hour, &temp_time.tm_min, &temp_time.tm_sec);
    temp_time.tm_year -= 1900;
    temp_time.tm_mon -= 1;
    temp_time.tm_hour -= 1;
    full_time = mktime(&temp_time);
    
    return full_time;
}

time_t extract_dt(char *input){
    
    struct tm temp_time;
    time_t    full_time;
    
    memset(&temp_time, 0, sizeof(struct tm));
    sscanf(input, "%4d%2d%2d",&temp_time.tm_year, &temp_time.tm_mon, &temp_time.tm_mday);
    temp_time.tm_year -= 1900;
    temp_time.tm_mon -= 1;
    full_time = mktime(&temp_time);
    
    return full_time;
}

time_t extract_from_to_dt(char *input){
    
    struct tm temp_time;
    time_t    full_time;
    
    memset(&temp_time, 0, sizeof(struct tm));
    sscanf(input, "%dT%dT%d",&temp_time.tm_year, &temp_time.tm_mon, &temp_time.tm_mday);
    temp_time.tm_year -= 1900;
    temp_time.tm_mon -= 1;
    full_time = mktime(&temp_time);
    
    return full_time;
}

time_t dt_increment(time_t temp_time)
{
    struct tm *tm = localtime(&temp_time);
    tm->tm_mday += 7;
    
    time_t full_time=mktime(tm);
    
    return full_time;
}


void repeat(event_t *temp_event, node_t *temp_node, node_t *head, char *rrule)
{
    
    char *pwd;
    char until_date [15];
    char location [MAX_LINE_LEN];
    char summary [MAX_LINE_LEN];
    
    pwd = strstr(rrule,"UNTIL");
    pwd+=6;
    strncpy(until_date,pwd,15);
    
    time_t curr_date = temp_event->dt_time;
    time_t curr_date_s = temp_event->dtstart;
    time_t curr_date_e = temp_event->dtend;
    strncpy(location,temp_event->location,MAX_LINE_LEN);
    strncpy(summary,temp_event->summary,MAX_LINE_LEN);
    time_t until = extract_dt(until_date);
    
    curr_date = dt_increment(curr_date);
    curr_date_s = dt_increment(curr_date_s);
    curr_date_e = dt_increment(curr_date_e);
    
    while(until >= curr_date){
        temp_event = emalloc(sizeof(event_t));
        temp_event->dt_time=curr_date;
        temp_event->dtstart=curr_date_s;
        temp_event->dtend=curr_date_e;
        strncpy(temp_event->location,location,MAX_LINE_LEN);
        strncpy(temp_event->summary,summary,MAX_LINE_LEN);
        temp_node= new_node(temp_event);
        head = add_inorder(head,temp_node);
        curr_date = dt_increment(curr_date);
        curr_date_s = dt_increment(curr_date_s);
        curr_date_e = dt_increment(curr_date_e);
        repeat_event++;
    }
    
    
}

int main(int argc, char *argv[])
{
    event_t *temp_event = NULL;
    node_t  *temp_node  = NULL;
    node_t  *head = NULL;
    
    
    int from_y = 0, from_m = 0, from_d = 0;
    int to_y = 0, to_m = 0, to_d = 0;
    char *filename = NULL;
    int i;
    
    for (i = 0; i < argc; i++) {
        if (strncmp(argv[i], "--start=", 7) == 0) {
            sscanf(argv[i], "--start=%d/%d/%d", &from_d, &from_m, &from_y);
        } else if (strncmp(argv[i], "--end=", 5) == 0) {
            sscanf(argv[i], "--end=%d/%d/%d", &to_d, &to_m, &to_y);
        } else if (strncmp(argv[i], "--file=", 7) == 0) {
            filename = argv[i]+7;
        }
    }

    
    if (from_y == 0 || to_y == 0 || filename == NULL) {
        fprintf(stderr,
                "usage: %s --start=dd/mm/yyyy --end=dd/mm/yyyy --file=icsfile\n",
                argv[0]);
        exit(1);
    }
    
    char from_date[MAX_LINE_LEN];
    char from_day[MAX_LINE_LEN];
    char to_date[MAX_LINE_LEN];
    char to_day[MAX_LINE_LEN];
    
    sprintf(from_date,"%dT%dT",from_y,from_m);
    sprintf(from_day,"%d",from_d);
    strcat(from_date,from_day);
    
    sprintf(to_date,"%dT%dT",to_y,to_m);
    sprintf(to_day,"%d",to_d);
    strcat(to_date,to_day);
    
    time_t start = extract_from_to_dt(from_date);
    time_t end = extract_from_to_dt(to_date);

    //inputting the file into the right nodes
    
    FILE *fpointer = fopen(filename,"r");
    
    char pw [MAX_LINE_LEN];
    char rrule [MAX_LINE_LEN];
   
   
    int event = 0;
    
    while (fgets(pw,MAX_LINE_LEN,fpointer)) {
        
        char *pwd = NULL;
        
        if(strncmp(pw,"BEGIN:VEVENT",11)==0){
            
            temp_event = emalloc(sizeof(event_t));
            
        }else if (strncmp(pw,"DTSTART:",8)==0){
            pwd = strstr(pw,":");
            pwd++;
            time_t dt = extract_dt(pwd);
            time_t dtstart = extract_dt_time(pwd);
            temp_event->dt_time = dt;
            temp_event->dtstart = dtstart;
        }else if(strncmp(pw,"DTEND:",6)==0){
            pwd = strstr(pw,":");
            pwd++;
            time_t dtend = extract_dt_time(pwd);
            temp_event->dtend = dtend;
        }else if(strncmp(pw,"RRULE:",6)==0){
            pwd = strstr(pw,":");
            pwd++;
            strncpy(rrule,pwd,MAX_LINE_LEN);
        }else if(strncmp(pw,"LOCATION:",9)==0){
            pw[strlen(pw)-1]=0;
            pwd = strstr(pw,":");
            pwd++;
            strncpy(temp_event->location,pwd,MAX_LINE_LEN);
        }else if(strncmp(pw,"SUMMARY:",8)==0){
            pw[strlen(pw)-1]=0;
            pwd = strstr(pw,":");
            pwd++;
            strncpy(temp_event->summary,pwd,MAX_LINE_LEN);
        }else if(strncmp(pw,"END:VEVENT",10)==0){
            temp_node = new_node(temp_event);
            head = add_inorder(head, temp_node);
            event++;
            if (strlen(rrule)>1){
                repeat(temp_event,temp_node,head,rrule);
                memset(rrule,'\0',MAX_LINE_LEN*sizeof(char));
            }
        }
    }
    
    
    print_event(head,start,end);
    
    
   int total_event = repeat_event + event;
   int j;
    
   for (j = 0; j < total_event ; j++){
       
        temp_node = head;
        assert(temp_node != NULL);
        head = remove_front(head);
        temp_event = temp_node->val;
        assert(temp_event != NULL);
        free(temp_event);
        free(temp_node);
    }
    
    assert(head==NULL);
    fclose(fpointer);
    

}

