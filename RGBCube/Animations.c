/*
 * Animations.c
 *
 * Created: 10.08.2016 21:43:43
 *  Author: Jonas
 */ 
#ifndef F_CPU
#define F_CPU 16000000UL
#endif
#include "Animations.h"
#include "Basics.h"
#include <util/delay.h> 

void simpleLightning2() {
	 bytes[0] = 0xfc;
	bytes[10] = 0x01;
	send();
	_delay_ms(50);
	bytes[10] = 0x02;
	send();
	_delay_ms(50);
	bytes[10] = 0x04;
	send();
	_delay_ms(50);
	bytes[10] = 0x08;
	send();
	_delay_ms(50);
	bytes[10] = 0x10;
	send();
	_delay_ms(50);
	bytes[10] = 0x20;
	send();
	_delay_ms(50);
	bytes[10] = 0x40;
	send();
	//_delay_ms(50);
	//bytes[1] = 0x80;
	//send();
	_delay_ms(200);
	
}

void simpleMultiplex() {
	switch(stateSimpleMultiplex) {
		case 0:
		bytes[0]=0x01;
		bytes[1]=0xff;
		bytes[2]=0x00;
		bytes[3]=0x00;
		bytes[4]=0xff;
		bytes[5]=0x00;
		bytes[6]=0x00;
		break;
		case 1:
		bytes[0]=0x02;
		bytes[1]=0x00;
		bytes[2]=0xff;
		bytes[3]=0x00;
		bytes[4]=0x00;
		bytes[5]=0xff;
		bytes[6]=0x00;
		break;
		case 2:
		bytes[0]=0x04;
		bytes[1]=0x00;
		bytes[2]=0x00;
		bytes[3]=0xff;
		bytes[4]=0x00;
		bytes[5]=0x00;
		bytes[6]=0xff;
		break;
		case 3:
		bytes[0]=0x08;
		bytes[1]=0xff;
		bytes[2]=0x00;
		bytes[3]=0x00;
		bytes[4]=0xff;
		bytes[5]=0x00;
		bytes[6]=0x00;
		break;
		case 4:
		bytes[0]=0x10;
		bytes[1]=0x00;
		bytes[2]=0xff;
		bytes[3]=0x00;
		bytes[4]=0x00;
		bytes[5]=0xff;
		bytes[6]=0x00;
		break;
		case 5:
		bytes[0]=0x20;
		bytes[1]=0x00;
		bytes[2]=0x00;
		bytes[3]=0xff;
		bytes[4]=0x00;
		bytes[5]=0x00;
		bytes[6]=0xff;
		break;
		case 6:
		bytes[0]=0x40;
		bytes[1]=0xff;
		bytes[2]=0x00;
		bytes[3]=0x00;
		bytes[4]=0xff;
		bytes[5]=0x00;
		bytes[6]=0x00;
		break;
		case 7:
		bytes[0]=0x80;
		bytes[1]=0x00;
		bytes[2]=0xff;
		bytes[3]=0x00;
		bytes[4]=0x00;
		bytes[5]=0xff;
		bytes[6]=0x00;
		break;
	}
	
	stateSimpleMultiplex++;
	if(stateSimpleMultiplex>7)
	stateSimpleMultiplex = 0;
	
	
	send();
	
}

void simpleLightning() {
	//uint8_t bytes[25];
	bytes[0] = 0x01;
	bytes[1] = 0x01;
	send();
	_delay_ms(50);
	bytes[1] = 0x02;
	send();
	_delay_ms(50);
	bytes[1] = 0x04;
	send();
	_delay_ms(50);
	bytes[1] = 0x08;
	send();
	_delay_ms(50);
	bytes[1] = 0x10;
	send();
	_delay_ms(50);
	bytes[1] = 0x20;
	send();
	_delay_ms(50);
	
}
