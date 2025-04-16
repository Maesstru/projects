
#include <stdio.h>
#include <string.h>

#include "repo.h"
#include "uiOptions.h"
#include "service.h"
#include "floaturi.h"

int citesteIntUi(char* prompt)
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


void app()
{
	Service* serv = initService();
	float pretFiltru = serviceGetPretFiltru(serv);
	int cantFiltru = serviceGetCantFiltru(serv);
	char* prodFiltru = serviceGetPrdFiltru(serv);
	while (1)
	{
		pretFiltru = serviceGetPretFiltru(serv);
		cantFiltru = serviceGetCantFiltru(serv);
		prodFiltru = serviceGetPrdFiltru(serv);
		printf("\n\t******************\n");
		printf("***   1. add\n");
		printf("***   2. modify\n");
		printf("***   3. delete\n");
		printf("***   4. print sorted\n");
		printf("***   5. schimba filtru\n");
		printf("***   6. afiseaza lista\n");
		printf("Filtru curent:\n");
		if(prodFiltru == NULL)
			printf("\tproducator: empty\n");
		else
			printf("\tproducator: %s\n", prodFiltru);
		if (floatEq(pretFiltru, -1))
			printf("\tpret: empty\n");
		else
			printf("\tpret: %.2f\n", pretFiltru);
		if (cantFiltru == -1)
			printf("\tcantitate: empty\n");
		else
			printf("\tpret: %d\n", cantFiltru);
		int cmd = citesteIntUi("cmd>>");
		
		if (cmd == 0)
		{
			printf("Byee!");
			break;
		}
		else if (cmd == 1)
		{
			add(serv);
		}
		else if (cmd == 2)
		{
			modify(serv);
		}
		else if (cmd == 3)
		{
			del(serv);
		}
		else if (cmd == 4)
		{
			printSorted(serv);
		}
		else if (cmd == 5)
		{
			schimbaFiltruUI(serv);
		}
		else if (cmd == 6)
		{
			afisLista(serv);
		}
		else
			printf("!!!   Comanda nu exista :(\n");
	}
	destructService(serv);
	
}