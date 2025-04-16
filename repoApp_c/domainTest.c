#include <stdlib.h>
#include <assert.h>
#include <string.h>

//local includes
#
#include "domain.h"
#include "floaturi.h"

void testConstr()
{
	int id = 1, cant = 5;
	float pret = (float)33.3;
	char* tip = "haz";
	char* producator = "dell";
	char* model = "a1234";



	Produs* prd = prod(id, tip, producator, model, pret, cant);

	assert(prd->id == id);
	assert(prd->cantitate == cant);
	assert(floatEq(prd->pret, pret));
	assert(!strcmp(prd->tip, tip));
	assert(!strcmp(prd->producator, producator));
	assert(!strcmp(prd->model, model));

	destructProd(prd);
}

void testeDomain()
{
	testConstr();
}