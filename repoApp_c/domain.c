
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

//local includes
#include "domain.h"


/*
functie care creeaza un produs cu atributele pasate
pre: 
	
post:
	aloca spatiu pentru produs si il initializeaza cu atributele pasate
	return:
		adresa produsului creat
*/
Produs* prod(int id, char* tip, char* producator, char* model, float pret, int cantitate)
{
	Produs* produs = malloc(sizeof(Produs));

	produs->id = id;
	produs->pret = pret;
	produs->cantitate = cantitate;

	produs->tip = malloc(strlen(tip) + 1);
	strcpy(produs->tip,tip);

	produs->model = malloc(strlen(model) + 1);
	strcpy(produs->model, model);

	produs->producator = malloc(strlen(producator) + 1);
	strcpy(produs->producator, producator);

	return produs;


}

/*
functie care dealoca intregul spatiu alocat unui produs
pre: 
	p: (Produs*) o adresa care indica spre un produs
post:
	dealoca memoria dedicata produsului p
*/
void destructProd(Produs* p)
{
	free(p->model);
	free(p->tip);
	free(p->producator);
	free(p);
}


