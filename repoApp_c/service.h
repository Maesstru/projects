#pragma once

#include "repo.h"
#include "domain.h"
#include "filtru.h"

typedef struct {
	Filtru* filtru;
	Lista* lista;
}Service;

Service* initService();
Filtru* serviceGetFiltru(Service* service);
void destructService(Service* service);
void serviceSchimbaFiltru(Service* service, char* producator, float pret, int cantitate);
int serviceMod(Service* service, int id, float pret_nou, int cant_nou);
int serviceAdauga(Service* service, int id, char* tip, char* producator, char* model, float pret, int cant);
int serviceInList(Service* service, int id);
int serviceDel(Service* service, int id);
Produs** serviceSort(Service* service, int reversed);
Lista* listaFiltrata(Service* service);
char* serviceGetPrdFiltru(Service* serv);
float serviceGetPretFiltru(Service* serv);
/// <summary>
/// 
/// 
/// </summary>
/// <param name="serv"></param>
/// <returns></returns>
int serviceGetCantFiltru(Service* serv);
