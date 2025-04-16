
int floatEq(float nr1, float nr2)
{
	if (nr1 - nr2 < 0.001 && nr1 - nr2 > -0.001)
		return 1;
	return 0;
}
