/*
 * Assignment4.c
 *
 * Created: 2019-12-03 11:22:31 AM
 * Author : Sho Masuda
 * V-number: V00877001
 */ 

#include <avr/io.h>
#include <alloca.h>
#include "CSC230.h"
#include <string.h>
#include <stdio.h>

#define  ADC_BTN_RIGHT 0x032
#define  ADC_BTN_UP 0x0C3
#define  ADC_BTN_DOWN 0x17C
#define  ADC_BTN_LEFT 0x22B
#define  ADC_BTN_SELECT 0x316

volatile uint8_t position = 3; // 0-16 
volatile uint8_t pos3 = 0; // 0-9
volatile uint8_t pos4 = 0; // 0-9
volatile uint8_t pos5 = 0; // 0-9 
volatile uint8_t sp = 0; // 0-9
volatile uint16_t speed = 0;//0-65535
volatile uint8_t sp_temp = 0; //0-9
volatile uint16_t count = 0; // 0-999 
volatile uint16_t value = 000; //0-65535
volatile uint8_t test = 0;//toggling test 
volatile unsigned short adc_result = 0; //0-
char Line1[17]; //Global Line1
char Line2[17]; //Global Line1



/*Getting ADC value as 16bits*/
void poll_adc(){
	
	adc_result = 0; //16 bits
	ADCSRA |= 0x40;
	while((ADCSRA & 0x40) == 0x40);
	
	unsigned short result_low = ADCL;
	unsigned short result_high = ADCH;
	
	adc_result = (result_high<<8)|result_low;
}

/*Checking button value, Assigning corresponding adc_value to right button*/
unsigned short button_check(){
	
	unsigned short button = 0;
	
	if(adc_result<ADC_BTN_RIGHT){
			button = 1;//RIGHT
		}else if(adc_result<ADC_BTN_UP){
			button = 2;//UP
		}else if(adc_result<ADC_BTN_DOWN){
			button = 3;//DOWN
		}else if(adc_result<ADC_BTN_LEFT){
			button = 4;//LEFT
		}else if(adc_result<ADC_BTN_SELECT){
			button = 5; //SELECT
		}else{
			button = 0;	
		}
		
	return button;

}

/*Updating Line 1*/
void updatel1(){
	
	sprintf(Line1," n=%d%d%d*    SP:%d",pos3,pos4,pos5,sp);
	
}


/*Updating Line2 */
void updatel2(){
	
	sprintf(Line2,"cnt:%3d v:%6d", count, value);
}

/*Blinking the curser*/ 
void blink(){
	if (test == 0){
		updatel1();
		test = 1;
	}else if(test == 1){
		memset(Line1+position,' ',1);
		test = 0;
	}

}

/*Assigning corresponding speed 16bit value*/
void speed_change(){
	if (sp == 0){
		speed = 65535;
	}else if(sp == 1){
		speed = 977;
	}else if(sp == 2){
		speed = 1953;
	}else if(sp == 3){
		speed = 3902;
	}else if(sp == 4){
		speed = 7812;
	}else if(sp == 5){
		speed = 15625;
	}else if(sp == 6){
		speed = 23437;
	}else if(sp == 7){
		speed = 31250;
	}else if(sp == 8){
		speed = 39062;
	}else if(sp == 9){
		speed = 46875;
	}
}

/*Converting 3 individual digits to one integer*/
void combine(){
	
	int hundreds = 0;
	int tens =0;
	int decimal =0;
	
	hundreds = pos3*100;
	tens = pos4*10;
	decimal = pos5;
	
	value = hundreds + tens + decimal;
	
}

/*Set up timer then enable*/
void execution(){
	
	combine();
	count = 1;
	
	speed_change();
	//Set up timer 4
	TCCR4A = 0;
	TCCR4B = (1<<CS12)|(1<<CS10);
	TCNT4 = 65535 - speed;
	TIMSK4 = 1<<TOIE4;
	
	sei();
	
}

/*Update values(pos3,pos4,pos5,sp,position) to each specific button */
void command(unsigned short button_result)
{
	
	//RIGHT
	if (button_result == 1){
		if (position == 3|| position == 4|| position == 5){
			position++;
		}else if(position == 6){
			position = 14;
		}else{
			position = 14;	
		}
	//UP	
	}else if (button_result == 2){
		if(position == 3){
			if(pos3==9){
				pos3 = 0;
				}else{
				pos3++;
			}
		}else if(position == 4){
			if(pos4==9){
				pos4 = 0;
			}else{
				pos4++;
			}
		}else if(position == 5){
			if(pos5==9){
				pos5 = 0;
			}else{
				pos5++;
			}
		}else if(position == 6){
			if(sp ==0){
				sp = 0;
			}else{
				execution();
			}	
		}else if(position == 14){
			if(sp==9){
				sp = 0;
			}else{
				sp++;
			}
		}
	//DOWN	
	}else if (button_result ==3){
		if(position == 3){
			if(pos3==0){
				pos3 = 9;
			}else{
				pos3--;
			}
		}else if(position == 4){
			if(pos4==0){
				pos4 = 9;
			}else{
				pos4--;
			}
		}else if(position == 5){
			if(pos5==0){
				pos5 = 9;
			}else{
				pos5--;
			}
		}else if(position == 6){
			if(sp ==0){
				sp = 0;
			}else{
				execution();
			}
		}else if(position == 14){
			if(sp==0){
				sp = 9;
			}else{
				sp--;
			}
		}
	//LEFT	
	} else if (button_result ==4){
		if (position == 4 || position == 5 || position == 6){
			position--;
		}else if(position == 3){
			position = 3;
		}else if(position == 14){
			position = 6;	
		}
	//SELECT	
	}else if (button_result ==5){	
			int temp = 0;
			temp = sp_temp;
			sp_temp = sp;
			sp = temp;
	}
	
}

/*Collatz sequence*/
void collatz(){
	
	count++;
	if(value%2 == 0){
		value = value/2;
	}else{
		value = 3*value + 1;
	}
	
		
}



int main()
{
	//TEST
	DDRL = 0b10101010;
	DDRB = DDRB | 0b00001010;

	PORTL = 0b10000000;
	PORTB = PORTB | 0b00001000;
	
	//ADC Set up
	ADCSRA = 0x87;
	ADMUX = 0x40;
	
	//Set up DIsplay 
	lcd_init();
	
	//Set up timer 1
	TCCR1A = 0;
	TCCR1B = (1<<CS12)|(1<<CS10);
	TCNT1 = 0xFFFF - 977;
	TIMSK1 = 1<<TOIE1;
	sei();
	

	//Set up timer 3
	TCCR3A = 0;
	TCCR3B = (1<<CS12)|(1<<CS10);
	TCNT3 = 0xFFFF - 7812;
	TIMSK3 = 1<<TOIE3;
	sei();
	
	char Intro1[17] ="Sho Masuda";
	char Intro2[17] ="CSC230 Fall 2019";


	//intro display
	lcd_xy(0,0);
	lcd_puts(Intro1);
	lcd_xy(0,1);
	lcd_puts(Intro2);
	_delay_ms(1000); //1sec
	
	lcd_init();
	
	unsigned short result = 0;
	unsigned short result1 = 0;

	
	here:
    while (1) 
    {
		PORTL = PORTL ^ 0b10100000;
		updatel2();//updates line2
		lcd_xy(0,0);
		lcd_puts(Line1);
		lcd_xy(0,1);
		lcd_puts(Line2);
		result = button_check(); //check for button value
		if (result == result1){  // avoding duplication of command
			goto here;
		}
		result1 = result;  
		command(result1); // execute command responds to the position and button value
    }
	
}



//button check ISR
ISR(TIMER1_OVF_vect)
{
	//constant 1sec
	TCNT1 = 0xFFFF - 977;
	poll_adc();
	
}




//COLLATZ sequence ISR
ISR(TIMER4_OVF_vect)
{
	
	speed_change();
	
	if (value == 1){
		speed = 0;
	}else{
		collatz();
	}
	
	TCNT4 = 65535-speed;
	
	
	
}



//BLINK ISR
ISR(TIMER3_OVF_vect)
{
	TCNT3 = 0xFFFF - 7812;
	
	blink();	
	
}



