
#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

//local includes
#include "serviceTest.h"
#include "service.h"
#include "floaturi.h"
#include "filtru.h"

void testServiceAdd()
{
	Service* srv = initService();

	Lista* l = srv->lista;

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	assert(l->len == 0);
	assert(serviceAdauga(srv, id, tip, producator, model, pret, cant) == 1);
	assert(l->len == 1);
	Produs* produs = l->vector[0];

	assert(produs->id == id);
	assert(produs->cantitate == cant);
	assert(produs->pret - pret < 0.000001 && produs->pret - pret > -0.000001);
	
	
	destructService(srv);
}

void testServiceMod()
{
	Service* srv = initService();

	Lista* l = srv->lista;

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	serviceAdauga(srv, id, tip, producator, model, pret, cant);

	Produs* prd = getById(l, id);

	float pret_nou = 3;
	int cant_nou = 12;

	assert(serviceMod(srv, -1, 0, cant_nou) == 0);
	assert(serviceMod(srv, id, prd->pret, prd->cantitate) == 0);

	assert(serviceMod(srv, id, 0, cant_nou) == 1);

	assert(prd->cantitate == cant_nou);
	assert(l->vector[0]->pret - pret < 0.0000001 && l->vector[0]->pret - pret > -0.00001);


	assert(serviceMod(srv, id, pret_nou, -1) == 1);

	assert(prd->cantitate == cant_nou);
	assert(l->vector[0]->pret - pret_nou < 0.0000001 && l->vector[0]->pret - pret_nou > -0.00001);

	assert(serviceMod(srv, id, pret, cant) == 2);

	assert(prd->cantitate == cant);
	assert(l->vector[0]->pret - pret < 0.0000001 && l->vector[0]->pret - pret > -0.00001);

	destructService(srv);
}

void testServiceInList()
{
	Service* srv = initService();

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	serviceAdauga(srv, id, tip, producator, model, pret, cant);

	assert(serviceInList(srv, id) == 1);
	assert(serviceInList(srv, id + 1) == 0);

	destructService(srv);
}

void testServiceDel()
{
	Service* srv = initService();

	Lista* l = srv->lista;

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	serviceAdauga(srv, id, tip, producator, model, pret, cant);

	assert(l->len == 1);
	serviceDel(srv, id);
	assert(l->len == 0);

	destructService(srv);
}

void testServiceSort()
{
	Service* srv = initService();

	Lista* l = srv->lista;

	int i;
	for (i = 0; i < 5; i++)
	{
		serviceAdauga(srv, i, "test", "test", "test", (float)i, i);
	}
	for (i=5; i < 10; i++)
	{
		serviceAdauga(srv, i, "test", "test", "test", 1, i);
	}
	serviceAdauga(srv, 10, "test", "test", "test", 3, 1);
	

	Produs** sorted;

	sorted = serviceSort(srv, 0);

	for (i = 0; i < l->len-1; i++)
	{
		
		if(floatEq(sorted[i]->pret, sorted[i+1]->pret))
		{
			assert(sorted[i]->cantitate <= sorted[i + 1]->cantitate);
		}else if (sorted[i]->pret > sorted[i + 1]->pret)
			assert(0);
	}
	free(sorted);

	sorted = serviceSort(srv, 1);
	for (i = 0; i < l->len-1; i++)
	{
		
		if (floatEq(sorted[i]->pret, sorted[i + 1]->pret))
		{
			assert(sorted[i]->cantitate >= sorted[i + 1]->cantitate);
		}else if (sorted[i]->pret < sorted[i + 1]->pret)
			assert(0);
	}

	free(sorted);
	destructService(srv);

}

void testServiceSchimbaFiltru()
{
	Service* serv = initService();


	char* producator = malloc(5);
	strcpy(producator, "Paul");
	float pret = 15;
	int cantitate = 5; 

	Filtru* f = serviceGetFiltru(serv);

	assert(floatEq(getPretFiltru(f), -1));
	assert(getProducatorFiltru(f) == NULL);
	assert(getCantitateFiltru(f) == -1);

	serviceSchimbaFiltru(serv, producator, pret, cantitate);

	

	assert(!strcmp(getProducatorFiltru(f), producator));
	assert(floatEq(getPretFiltru(f), pret));
	assert(getCantitateFiltru(f) == cantitate);

	destructService(serv);

}

void testFiltrare()
{
	Service* serv = initService();
	Lista* l = serv->lista;

	char* nume1 = "Paul";
	char* nume2 = "Andrei";
	char* nume3 = "Victor";

	char* nume[3];
	nume[0] = nume1;
	nume[1] = nume2;
	nume[2] = nume3;

	for (int i = 0; i < 10; i++)
	{
			serviceAdauga(serv , i, nume[i % 3], nume[i % 3], nume[i % 3], (float)i, i);

	}
	assert(l->len == 10);
	
	char* paul = malloc(5);
	strcpy(paul, "Paul");
	serviceSchimbaFiltru(serv, paul, (float) - 1, -1);
	

	Lista* filtrat = listaFiltrata(serv);

	assert(filtrat->len == 4);

	int i;
	for (i = 0; i < filtrat->len; i++)
		assert(!strcmp(filtrat->vector[i]->producator, getProducatorFiltru(serv->filtru)));

	free(filtrat->vector);
	free(filtrat);
	destructService(serv);
}

void testeService()
{
	testServiceAdd();
	testServiceMod();
	testServiceInList();
	testServiceDel();
	testServiceSort();
	testServiceSchimbaFiltru();
	testFiltrare();
}