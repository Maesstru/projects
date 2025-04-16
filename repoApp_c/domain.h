#pragma once

typedef struct
{
	int id;
	char* tip;
	char* producator;
	char* model;
	float pret;
	int cantitate;
}Produs;

Produs* prod(int id, char* tip, char* produs, char* model, float pret, int cantitate);
void destructProd(Produs* p);
void printProdus(Produs* produs);
