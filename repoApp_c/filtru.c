
#include <stdlib.h>
#include <stdio.h>

#include "filtru.h"


/*
functie care initializeaza un filtru
pre:
	-
post:
	aloca spatiu pentru filtru
	-return: adresa spatiului alocat pentru filtru
*/
Filtru* initFiltru()
{
	Filtru* filtru = malloc(sizeof(Filtru));
	filtru->cantitate = -1;
	filtru->pret = -1;
	filtru->producator = NULL;
	return filtru;
}

/*
functie care schimba atributele filtrului
pre:
	f: (Filtru*)adresa catre un filtru
	producator:(char*) sir de caractere
	pret: (float)
	cant: (int) 
post:
	f.producator <- producator
	f.pret <- pret
	f.cantitate <- cant
*/
void schimbaFiltru(Filtru* f, char* producator, float pret, int cant)
{
	free(f->producator);

	f->cantitate = cant;
	f->producator = producator;
	f->pret = pret;
}

/*
functie care returneaza producatorul
pre:
	f: filtru
post:
	return: (char*) producator
*/
char* getProducatorFiltru(Filtru* f)
{
	return f->producator;
}

/*
functie care returneaza pretul setat in filtru
pre:
	f: filtru
post: 
	return: (float) pretul setat in filtru
*/
float getPretFiltru(Filtru* f)
{
	return f->pret;
}

/*
functie care returneaza cantitatea setata in filtru
pre: 
	f: filtru
post:
	return: (int) cantitatea setata in filtru
*/
int getCantitateFiltru(Filtru* f)
{
	return f->cantitate;
}

void destructFiltru(Filtru* f)
{
	if(f->producator != NULL)
		free(f->producator);
	free(f);
}
