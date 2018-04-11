/*
 * Basics.h
 *
 * Created: 26.01.2016 13:33:26
 *  Author: jomo894
 */ 
#ifndef BASICS_H
#define BASICS_H
#include <avr/io.h> 

#define START -1
#define END 25


#define ERROR 0
#define VALID 1
#define CONTINUE 2

#define ANODE_0 PC0 //23 //D14
#define ANODE_1 PC1 //24 //D15
#define ANODE_2 PC2 //25 //D16
#define ANODE_3 PC3 //26 //D17
#define ANODE_4 PC4 //27 //D18
#define ANODE_5 PC5 //28 //D19
#define ANODE_6 PD6 //10 //D6
#define ANODE_7 PD7 //11 //D7

extern uint8_t byteStream[];


//uint8_t buffer[25];
int16_t STATE;
uint8_t anodes;
uint8_t cathodes[24];
uint8_t bytes[25];

 
void spi_init(void);
void gpio_init(void);
uint8_t runStateMachine(uint8_t c);  
void parse();
void updateCube();
void send();
void spi_send(uint8_t data);

#endif 