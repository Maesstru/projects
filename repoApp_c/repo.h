#pragma once
#include "domain.h"

typedef struct
{
	Produs** vector;
	int len;
	int cap;
}Lista;

Lista* initLista();
void destructLista(Lista*);
void resize(Lista*);
void addRepo(Lista* list, Produs* produs);
void modRepo(Produs* produs, float pret_nou, int cant_noua);
Produs* getById(Lista*, int id);
Produs* delRepo(Lista* list, Produs* produs);
