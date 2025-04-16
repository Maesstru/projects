
#include <assert.h>
#include <stddef.h>

//local includes
#include "repoTest.h"
#include "repo.h"
#include "domain.h"
#include "floaturi.h"


void testAdd()
{
	Lista* l = initLista();

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	assert(l->len == 0);

	Produs* prd = prod(id, tip, producator, model, pret, cant);

	//daca addRepo <- 1 inseamna ca functia a adaugat cu succes element
	addRepo(l, prd);
	assert(l->len == 1);

	Produs* prd2 = prod(id + 1, tip, producator, model, pret, cant);

	addRepo(l, prd2);
	assert(l->len == 2);


	assert(l->vector[1]->id == id + 1);
	assert(l->vector[0]->id == id);

	int i;
	for (i = 0; i < 10; i++)
	{
		assert(l->len == i + 2);
		Produs* prd3 = prod(i+3, tip, producator, model, pret, cant);
		addRepo(l, prd3);
	}

	destructLista(l);

}

void testMod()
{
	Lista* l = initLista();

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	Produs* prd = prod(id, tip, producator, model, pret, cant);

	addRepo(l, prd);

	int cant_noua = 10;
	float pret_nou = 5;

	modRepo(prd, pret_nou, cant_noua);

	assert(l->vector[0]->cantitate == cant_noua);
	assert(floatEq(l->vector[0]->pret, pret_nou));
	
		
	destructLista(l);
}

void testDel()
{
	Lista* l = initLista();

	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";

	Produs* prd = prod(id, tip, producator, model, pret, cant);
	Produs* prd2 = prod(id+1, tip, producator, model, pret, cant);

	addRepo(l, prd);
	addRepo(l, prd2);

	assert(l->len == 2);

	assert(delRepo(l, prd) == prd);

	assert(l->len == 1);
	assert(getById(l, id) == NULL);

	assert(l->vector[0] == prd2);

	destructProd(prd);

	destructLista(l);
}

void testeRepo()
{
	testAdd();
	testMod();
	testDel();
}