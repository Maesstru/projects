#pragma once

typedef struct
{
	char* producator;
	float pret;
	int cantitate;
}Filtru;

Filtru* initFiltru();
void destructFiltru(Filtru* filtru);
void schimbaFiltru(Filtru* filtru, char* producator, float pret, int cantitate);
float getPretFiltru(Filtru* filtru);
int getCantitateFiltru(Filtru* filtru);
char* getProducatorFiltru(Filtru* filtru);

