/*
*
* Created: 25.01.2016 14:37:46
* Author : jomo894
*/

#define F_CPU 16000000UL        //Says to the compiler which is our clock frequency, permits the delay functions to be very accurate

#include <avr/io.h>            //General definitions of the registers values
#include <util/delay.h>            //This is where the delay functions are located
#include <avr/interrupt.h>
#include "Basics.h"
#include "Animations.h"

//#define BAUD_PRESCALE (1) //1000000, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (3) //500000, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (7) //250000, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (8) //230400 U2X0=1, Error=0.0% @16MHz
#define BAUD_PRESCALE (16) //115200Baud, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (25) //76800Baud, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (51) //38400Baud, U2X0=1, Error=0.0% @16MHz
//#define BAUD_PRESCALE (103) //19200Baud, U2X0=1, Error=0.0% @16MHz

//#define BAUD_PRESCALE (3) //460800Baud, U2X0=1, Error=0.0% @14.7MHz
//#define BAUD_PRESCALE (15) //115200, U2X0=1, Error=0.0% @14.7MHz
//#define BAUD_PRESCALE (7) //230400, U2X0=1, Error=0.0% @14.7MHz

//we need baudrate >= 250k for stable visualization (frequency 1000Hz * 270Bit = 270000Baud)
//HC-06 supports 230400bps (too slow, in fact) and 460800bps (sufficient)
//atmega328p cannot generate this baudrate with 16MHz, so quartz needed to change to 14.7MHz :)

//Pin 30 (PD0): RXD --> 01
//Pin 31 (PD1): TXD --> 02

//Pin 15: (PB3) MOSI --> D11
//Pin 16: (PB4) MISO  --> D12
//Pin 17: (PB5) SCK --> D13




uint16_t numOfInvalidPackages = 0;
uint16_t numOfValidPackages = 0;



void uart_putc(char c) {
	while( ( UCSR0A & ( 1 << UDRE0 ) ) == 0 ){}
	// write the byte to the serial port
	UDR0 = c;
}
void uart_puts (const char *s)
{
	while (*s) {
		uart_putc(*s);
		s++;
	}
}
uint8_t uart_getc(void)
{
	while (!(UCSR0A & (1<<RXC0)))   // warten bis Zeichen verfuegbar
	;
	return UDR0;                   // Zeichen aus UDR an Aufrufer zurueckgeben
}

void uart_init() {
	UCSR0B |= (1<<RXEN0)  | (1<<TXEN0);
	UCSR0C |= (1<<UCSZ00) | (1<<UCSZ01);
	UCSR0A |= (1<<U2X0);	
	UBRR0H  = (BAUD_PRESCALE >> 8);
	UBRR0L  = BAUD_PRESCALE;
}

void checkSM() {
	runStateMachine('a');
	runStateMachine(0xff);
	for(uint8_t i = 0; i<8; i++){
		runStateMachine(0xff);
		runStateMachine(0x00);
		runStateMachine(0x00);

	}
		if(runStateMachine('e')==VALID){
		parse();
		updateCube();
	}
}






void sendStatistics() {
	uart_puts("#valid: ");
	char buffer[15];
	utoa( numOfValidPackages, buffer, 10 );
	uart_puts(  buffer);
	uart_puts(", #invalid: ");
	utoa( numOfInvalidPackages, buffer, 10 );
	uart_puts( buffer);
	uart_puts(", ratio: ");
	dtostrf((numOfInvalidPackages/1.0)/(numOfInvalidPackages+numOfValidPackages),6,3,buffer);
	uart_puts(buffer);
	uart_puts("\r\n");
	
}
void data_from_uart() {
	uint8_t c = uart_getc();
	uint8_t result = runStateMachine(c);
	if(result==VALID) {
		parse();
		updateCube();
		numOfValidPackages++;
	}
	else if(result == ERROR) {
		numOfInvalidPackages++;
	}
	if(numOfInvalidPackages > 9999 || numOfValidPackages > 9999) {
		sendStatistics();
		numOfInvalidPackages=0;
		numOfValidPackages=0;
	}
}

void data_from_onboard() {
	simpleMultiplex();
	//simpleLightning();
	simpleLightning2();
}


int main(void)
{
	
	
	uart_init();
	spi_init();
	gpio_init();
	
	STATE = START;
	checkSM();
	while (1){
		data_from_uart();
		//data_from_onboard();
		
		
	}
}


