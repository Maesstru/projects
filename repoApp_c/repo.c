#include <stdlib.h>

//local includes
#include "domain.h"
#include "repo.h"
/*
functie care initializeaza dinamica o lista
params: -
postconditii: returneaza pointer la Lista alocata dinamic
*/
Lista* initLista()
{
	Lista* l = malloc(sizeof(Lista));

	l->cap = 4;
	l->len = 0;
	l->vector = malloc(l->cap * sizeof(Produs*));

	return l;
}

/*
*functie care dealoca spatiul alocat pentru intreaga lista
params: l(Lista*)
preconditii: l de tip Lista* alocata dinamic
postconditii: spatiul alocat pentru intreg l-ul este dealocat
*
*/
void destructLista(Lista* l)
{
	int i;
	for (i = 0; i < l->len; i++)
	{
		destructProd(l->vector[i]);
	}

	free(l->vector);
	free(l);
}

void resize(Lista* lst)
{

	Produs** biggerLst = malloc(2 * lst->cap * sizeof(Produs*));
	int i;
	for (i = 0; i < lst->cap; i++)
	{
		biggerLst[i] = lst->vector[i];

	}

	lst->cap = 2 * lst->cap;

	free(lst->vector);

	lst->vector = biggerLst;

}

/*
* functie care adauga elementul produs in lista
* params:-l(Lista*) lista de produse
*		 -produs(Produs*) elementul care va fi adaugat
* preconditii:-l de tip Lista* valida
*			  -produs de tip Produs* non-existent in lista l
* postconditii:-produsul a fost adaugat in lista
*/

void addRepo(Lista* lista, Produs* produs)
{

	if (lista->cap == lista->len)
		resize(lista);

	lista->vector[lista->len] = produs;
	lista->len++;

}

/*
* functie care returneaza elementul cu id-ul id
* params:-l(Lista*) lista de produse
*		 -id(int) id-ul elementului dorit
* preconditii:-l de tip Lista* 
*			  -id numar intreg
* postconditii:-returneaza produsul cu id-ul id, daca exista in lista
*			   -returneaza NULL, altfel	
*/
Produs* getById(Lista* l, int id)
{

	int i;
	for (i = 0; i < l->len; i++)
		if (l->vector[i]->id == id)
			return l->vector[i];

	return NULL;
}

/*
* functie care modifica cantitatea elementul cu id-ul id
* params:-prod(Produs*) un element existent in lista
*		 -pret_nou(float) pret nou
*		 -cant_noua(int) cantitatea noua
* preconditii:-prod pointer
*			  -pret_nou float
*			  -cant_noua numar intreg
* postconditii:-produsul a fost schimbat
*/
void modRepo(Produs* prod, float pret_nou, int cant_noua)
{
	prod->pret = pret_nou;
	prod->cantitate = cant_noua;
	
}

/*
functie care sterge elementul din lista
preconditii:
	l: lista
	p: produs existent in lista
postconditii:
	elementul va fi eliminat din lista
	lungimea listei va fi decrementata
	return: pointer spre produsul eliminat din lista
*/
Produs* delRepo(Lista* l, Produs* p)
{
	int i;
	for (i = 0; i < l->len; i++)
	{
		if (l->vector[i] == p)
			break;
	}
	int j;
	for (j = i; j < l->len; j++)
	{
		l->vector[j] = l->vector[j + 1];
	}
	l->len--;
	
	return p;
}
