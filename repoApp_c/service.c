
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "service.h"
#include "repo.h"
#include "floaturi.h"

/*
functie care aloca spatiu pt service
*/
Service* initService()
{
	Service* serv = malloc(sizeof(Service));
	Filtru* f = initFiltru();
	serv->filtru = f;
	Lista* list = initLista();
	serv->lista = list;
	return serv;
}
/*
functie care dealoca service
*/
void destructService(Service* serv)
{
	destructFiltru(serv->filtru);
	destructLista(serv->lista);
	free(serv);
}


/*
functie care returneaza filtru
post:
	return: (Filtru*) filtru
*/
Filtru* serviceGetFiltru(Service* serv)
{
	return serv->filtru;
}

/*
functie care returneaza producator filtru
post:
	return: (char*) filtru.producator
*/
char* serviceGetPrdFiltru(Service* serv)
{
	Filtru* f = serviceGetFiltru(serv);
	return f->producator;
}

/*
functie care returneaza pret filtru
post:
	return: (float) filtru.pret
*/
float serviceGetPretFiltru(Service* serv)
{
	Filtru* f = serviceGetFiltru(serv);
	return f->pret;
}

/*
functie care returneaza cantitate filtru
post:
	return: (int) filtru.cantitate
*/
int serviceGetCantFiltru(Service* serv)
{
	Filtru* f = serviceGetFiltru(serv);
	return f->cantitate;
}

/*
functie care schimba filtrul
*/
void serviceSchimbaFiltru(Service* serv, char* produc, float pret, int cant)
{
	Filtru* f = serviceGetFiltru(serv);

	if (!strcmp(produc, ""))
	{
		free(produc);
		produc = NULL;
	}
	schimbaFiltru(f, produc, pret, cant);
}

/*
functie care filtreaza lista dupa filtrul din service
pre:
	service: (Service*) service
	lista: (Lista*) lista
post:
	return: (Lista*) o lista care contine elementele filtrate
*/
Lista* listaFiltrata(Service* service)
{
	Lista* lista = service->lista;
	Filtru* f = serviceGetFiltru(service);
	Lista* filtrat = initLista();

	char* prodFiltru = getProducatorFiltru(f);
	float pretFiltru = getPretFiltru(f);
	int cantFiltru = getCantitateFiltru(f);

	int i;
	for (i = 0; i < lista->len; i++)
	{
		Produs* prod = lista->vector[i];
		if (prodFiltru == NULL || !strcmp(prod->producator, prodFiltru))
			if (floatEq(prod->pret, pretFiltru) || floatEq(pretFiltru, -1))
				if (prod->cantitate == cantFiltru || cantFiltru == -1)
					addRepo(filtrat, prod);
	}

	return filtrat;
}

/*
functie care adauga un element nou sau modifica un element existent
params:l(Lista*) lista, id, tip, producator, model, pret, cantitate
preconditii: -l pointer la lista
			 -id, cantitate: int-uri, atribute ale produsului
			 -tip, producator, model: char*, atribute ale produsului
			 -pret: float, atribut al produsului
postconditii: adauga produsul rezultat in lista
			  return: lungimea listei
		
*/
int serviceAdauga(Service* service, int id, char* tip, char* producator, char* model, float pret, int cant)
{
	Lista* l = service->lista;

	Produs* prd = prod(id, tip, producator, model, pret, cant);
	addRepo(l, prd);
	return l->len;

}

/*
functie care modifica un produs din lista
preconditii: -l: pointer la Lista
			 -id: int
			 -pret_nou: float
			 -cant: int
return: 
	nr_modificari: a fost gasit elementul cu id-ul id
	0: nu exista  elementul cu id-ul id
postconditii:
	pretul nemodificat: pret_nou <= 0 sau pret_nou == pret_vechi
	cantitate nemodificat: cant_nou < 0 sau cant_nou == cant_vechi
*/
int serviceMod(Service* service, int id, float pret_nou, int cant)
{
	Lista* l = service->lista;

	Produs* prd = getById(l, id);
	if (prd == NULL)
		return 0;
	int k = 2;
	if (pret_nou < 0.000001 || floatEq(pret_nou, prd->pret))
	{
		k--;
		pret_nou = prd->pret;
	}
	if (cant < 0 || cant == prd->cantitate)
	{
		k--;
		cant = prd->cantitate;
	}
	modRepo(prd, pret_nou, cant);
	return k;
}

/*
functie care verifica daca elementul cu id-ul id se afla in lista
preconditii: 
	-l: (Produs*) o lista initializata
	-id: (int) numar intreg
postconditii:
	-return: 1: exista produs cu id id
			 0: altfel
*/
int serviceInList(Service* service, int id)
{
	Lista* l = service->lista;

	Produs* prd = getById(l, id);
	if (prd == NULL)
		return 0;
	return 1;
}

/*
functie care sterge elementul cu id-ul id
preconditii:
	id: (int), reprezinta un id valabil
	l: (Lista*), contine produs cu id-ul id
postconditii:
	-elimina elementul cu id-ul id
	-returneaza: lungimea listei
*/
int serviceDel(Service* service, int id)
{
	Lista* l = service->lista;

	Produs* prd = getById(l, id);
	Produs* produsInAer = delRepo(l, prd);
	destructProd(produsInAer);
	return l->len;
}

int inRelatie(Produs* a, Produs* b, int reversed)
{
	if (a->pret > b->pret && !reversed)
		return 0;
	if (a->pret < b->pret && reversed)
		return 0;
	if (floatEq(a->pret, b->pret))
	{
		if (a->cantitate > b->cantitate && !reversed)
			return 0;
		if (a->cantitate < b->cantitate && reversed)
			return 0;
	}
		
	return 1;
}

/*
functie care ordoneaza crescator/descrescator  dupa pret,cantitate lista
preconditii:
	l: (Lista*)
	reversed: (int) 0 sau diferit de 0;
postconditii:
	return: pointer catre o lista ordonata dupa criterii
*/
Produs** serviceSort(Service* service, int reversed)
{
	Lista* l = service->lista;

	Produs** rez = malloc(l->len * sizeof(Produs*));
	int len_rez = 1;
	rez[0] = l->vector[0];
	int i;
	for (i = 1; i < l->len; i++)
	{
		int j=len_rez-1;
		while (j>=0 && !inRelatie( rez[j], l->vector[i], reversed))
			j--;
		int z;
		for (z = len_rez; z > j+1; z--)
		{
			rez[z] = rez[z - 1];
		}
		rez[j+1] = l->vector[i];
		len_rez++;
		

	}
	return rez;
}
