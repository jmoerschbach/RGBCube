/*
 * Basics.c
 *
 * Created: 26.01.2016 13:32:56
 *  Author: jomo894
 */ 
#include "Basics.h"


 uint8_t byteStream[] =   {   0xff,   0x00,   0x00,   0xff,   0x00,   0x00,
	0xff,   0x00,   0x00,   0xff,   0x00,   0x00,   0xff,   0x00,
	0x00,   0xff,   0x00,   0x00,   0xff,   0x00,   0x00,   0xff,
	0x00,   0x00,   0xff
};
uint8_t runStateMachine(uint8_t c) {
	uint8_t status = CONTINUE;
	switch (STATE) {
		case START:
			if (c == 'a') {
				STATE = 0;
			}
			break;
		case END:
			if (c == 'e') {
				status = VALID;
			}
			else {
				status = ERROR;
			}
			STATE = START;
			break;
		
		default:
			byteStream[STATE] = c;
			STATE++;
			status = CONTINUE;
	}
	return status;
}
void send() {
	runStateMachine('a');
	for(uint8_t i = 0; i<25; i++) {
		runStateMachine(bytes[i]);
	}
	if(runStateMachine('e')==VALID){
		parse();
		updateCube();
	}
}

void parse() {
	anodes = byteStream[0];
	for (uint8_t i = 1; i < 25; i++)
	{
		cathodes[i-1] = byteStream[i];
	}
}

void setLayerOn(uint8_t layerNr) {
	switch(layerNr) {
		case 0:
		PORTC |= (1<< ANODE_0);
		break;
		case 1:
		PORTC |= (1<< ANODE_1);
		break;
		case 2:
		PORTC |= (1<< ANODE_2);
		break;
		case 3:
		PORTC |= (1<< ANODE_3);
		break;
		case 4:
		PORTC |= (1<< ANODE_4);
		break;
		case 5:
		PORTC |= (1<< ANODE_5);
		break;
		case 6:
		PORTD |= (1<< ANODE_6);
		break;
		case 7:
		PORTD |= (1<< ANODE_7);
		break;
	}
}

void setLayerOff(uint8_t layerNr) {
	switch(layerNr) {
		case 0:
		PORTC &= ~(1<< ANODE_0);
		break;
		case 1:
		PORTC &= ~(1<< ANODE_1);
		break;
		case 2:
		PORTC &= ~(1<< ANODE_2);
		break;
		case 3:
		PORTC &= ~(1<< ANODE_3);
		break;
		case 4:
		PORTC &= ~(1<< ANODE_4);
		break;
		case 5:
		PORTC &= ~(1<< ANODE_5);
		break;
		case 6:
		PORTD &= ~(1<< ANODE_6);
		break;
		case 7:
		PORTD &= ~(1<< ANODE_7);
		break;
	}
}

void setAllLayersOff() {
	for(uint8_t i = 0; i < 8; i++) {
		setLayerOff(i);
	}
}

void updateCathodes() {
	PORTD &= ~(1<<PD2); //ST_CP down
	for(uint8_t i = 0; i < 24; i++) {
		spi_send(cathodes[i]);
	}
	PORTD |= (1<<PD2); //ST_CP high to shift contents of shift registers to storage registers
}
void updateAnodes() {
	for (uint8_t i = 0; i < 8; i++) {
		if (anodes & (1 << i))
			setLayerOn(i);
		else
			setLayerOff(i);
	}
}
void updateCube() {
	setAllLayersOff();
	updateCathodes();
	updateAnodes();
}


void gpio_init() {
	DDRC |= (1<< ANODE_0) | (1<<ANODE_1)|(1<< ANODE_2) | (1<<ANODE_3)|(1<< ANODE_4) | (1<<ANODE_5);
	DDRD |= (1<< ANODE_6) | (1<<ANODE_7);
	DDRD |= (1<<PD3);//MR as output
	PORTD|= (1<<PD3); //MR is always high
	DDRD |=(1<<PD2); // ST_CP as output
}

//SPI init
void spi_init(void)
{
	//set MOSI (DS), SCK (SH_CP) and SS as output
	DDRB |= (1<<PB3)|(1<<PB5)|(1<<PB2);
	//set SS to high
	PORTB |= (1<<PB2);
	//enable master SPI at clock rate Fck/2
	SPCR = (1<<SPE)|(1<<MSTR);//|(1<<SPR0);
	SPSR |=(1<<SPI2X);
}

//master send function
void spi_send(uint8_t data)
{
	//select slave
	PORTB &= ~(1<<PB2);
	//send data
	SPDR=data;
	//wait for transmition complete
	while (!(SPSR &(1<<SPIF)));
	//SS to high
	PORTB |= (1<<PB2);
}