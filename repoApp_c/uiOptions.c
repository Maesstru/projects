#include <stdio.h>
#include <string.h>
#include <stdlib.h>



//local includes
#include "repo.h"
#include "domain.h"
#include "service.h"

//header
#include "uiOptions.h"

/*
functie care printeaza produsul
pre:
	p: pointer spre un produs
post:
	afiseaza pe ecran id: tip, producator, model, pret, cantitate
*/
void printProdus(Produs* p)
{
	printf("%d: %s, %s, %s, %.2f, %d\n", p->id, p->tip, p->producator, p->model, p->pret, p->cantitate);
}

char* alocaString(char* string)
{
	char* dinamic = malloc(strlen(string) + 1);
	if(dinamic != NULL)
		strcpy(dinamic, string);

	return dinamic;
}

char* citesteString(char* prompt, char* buffer)
{
	printf("%s", prompt);
	fgets(buffer, sizeof(buffer), stdin);
	buffer[strlen(buffer)-1] = '\0';
	return alocaString(buffer);
}

int citesteInt(char* prompt)
{
	int nr;
	printf("%s", prompt);
	int res;
	do
	{
		res = scanf("%d", &nr);
		getc(stdin);
		if (res == 0)
			printf("^^introdu numar intreg: ");
	} while (res == 0);
	return nr;
}

float citesteFloat(char* prompt)
{
	float nr;
	printf("%s", prompt);
	int res;
	do
	{
		res = scanf("%f", &nr);
		getc(stdin);
		if (res == 0)
			printf("^^introdu numar cu/fara virgula: ");
	} while (res == 0);
	return nr;
}

void add(Service* service)
{
	printf("***      Adauga element      ***\n");

	int id, cant;
	float pret;
	char buffer[101];
	char* tip, * producator, * model;

	id = citesteInt("id: ");

	if (serviceInList(service, id))
	{
		printf("***    Id-ul introdus deja exista     ***\n");
		printf("***  doresti sa modifici cantitatea?  ***\n");
		int cmd = -1;

		while(cmd != 0 && cmd != 1)
			cmd = citesteInt("(1/0)>>");

		if (cmd == 1)
		{
			int cant_noua = citesteInt("cantitate noua: ");
			int rez = serviceMod(service, id, -1, cant_noua);
			printf("S-au produs %d modificari\n", rez);
		}


		return;
	}

	tip = citesteString("tip: ", buffer);

	producator = citesteString("producator: ", buffer);

	model = citesteString("model: ", buffer);

	pret = citesteFloat("pret: ");

	cant = citesteInt("cantitate: ");

	int rez = serviceAdauga(service, id, tip, producator, model, pret, cant);

	printf("Acum ai %d produse.\n", rez);

	free(tip);
	free(producator);
	free(model);
	
}

void modify(Service* service)
{
	printf("***              modifica element dupa id            ***\n");
	printf("***    introdu nr < 0 pentru a nu modifica atributul ***\n");
	int id, cant_nou;
	float pret_nou;

	id = citesteInt("id: ");

	if (!serviceInList(service, id))
	{
		printf("***          Nu exista niciun produs cu acest id             ***\n");
		printf("***   Apasa orice tasta pentru a reveni la meniul princpial  ***\n");
		getc(stdin);
		return;
	}

	pret_nou = citesteFloat("pret nou: ");

	cant_nou = citesteInt("cantitate noua: ");

	int rez = serviceMod(service, id, pret_nou, cant_nou);

	printf("S-au produs %d modificari\n", rez);
}

void del(Service* service)
{
	printf("***              sterge element dupa id            ***\n");

	int id;

	id = citesteInt("id: ");

	Lista* myList = service->lista;

	if (!getById(myList, id))
	{
		printf("***          Nu exista niciun produs cu acest id             ***\n");
		printf("***   Apasa orice tasta pentru a reveni la meniul princpial  ***\n");
		getc(stdin);
		return;

	}

	serviceDel(service, id);

	printf("***  Stergere efectuata cu succes!  ***\n");

}

void printSorted(Service* service)
{

	int reversed = -1;
	while (reversed != 0 && reversed != 1)
	{
		reversed = citesteInt("crescator/descrescator\n(0/1)>>");
	}
	Lista* myList = service->lista;
	Produs** sorted = serviceSort(service, reversed);
	printf("***     Lista sortata dupa pret,cantitate  ***\n");
	if (reversed)
		printf("***   \tdescrescator\n");
	else
		printf("***   \tcrescator\n\n");
	printf("***   id: tip, producator, model, pret, cantitate\n\n");
	int i;
	for (i = 0; i < myList->len; i++)
	{
		printf("***   ");
		printProdus(sorted[i]);
	}
	free(sorted);
}


void schimbaFiltruUI(Service* service)
{
	printf("***              modifica filtru            ***\n");


	char buffer[101];
	printf("Lasa gol pentru a anula filtrare dupa producator\n");
	char* producator = citesteString("producator: ", buffer);
	printf("Pune -1 pentru a anula filtrare dupa pret/cantitate\n");
	float pret = citesteFloat("pret: ");
	int cant = citesteInt("cantitate: ");

	serviceSchimbaFiltru(service, producator, pret, cant);
}

void afisLista( Service* serv)
{
	printf("***              Lista:            ***\n");


	Lista* filtrat = listaFiltrata(serv);

	int i;
	for (i = 0; i < filtrat->len; i++)
		printProdus(filtrat->vector[i]);

	free(filtrat->vector);
	free(filtrat);
}
