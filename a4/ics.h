#ifndef _ICS_H_
#define _ICS_H_
#include <time.h>

#define DT_LEN       17
#define SUMMARY_LEN  80
#define LOCATION_LEN 80
#define RRULE_LEN    80

typedef struct event_t
{
    time_t dt_time;
    time_t dtstart;
    time_t dtend;
    char summary[SUMMARY_LEN];
    char location[LOCATION_LEN];
} event_t;

#endif
