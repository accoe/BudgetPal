/*
   Program bazuje na kodzie: Takuji Nishimura i Makoto Matsumoto
   http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/MT2002/CODES/mt19937ar.c
*/
#include <stdio.h>
#include <iostream>
#include <string>

class MersenneTwister {
    int N;
    int M;
    int MATRIX_A;
    int UPPER_MASK;
    int LOWER_MASK;

    unsigned int *mt;
    int mti;

public:
    MersenneTwister(unsigned int s)
    {
        N = 624;
        M  = 397;
        MATRIX_A = 0x9908b0dfUL ;
        UPPER_MASK = 0x80000000UL;
        LOWER_MASK = 0x7fffffffUL;
        mti=N+1;

        mt = new unsigned int[N];
        mt[0]= s & 0xffffffffUL;

        for (mti=1; mti<N; mti++) {
            mt[mti] =
                (1812433253UL * (mt[mti-1] ^ (mt[mti-1] >> 30)) + mti);
            mt[mti] &= 0xffffffffUL;
        }
    }


    unsigned int Int32(void)
    {
        unsigned int y;
        static unsigned int mag01[2]= {0x0UL, MATRIX_A};
        if (mti >= N) {
            int kk;
            for (kk=0; kk<N-M; kk++) {
                y = (mt[kk]&UPPER_MASK)|(mt[kk+1]&LOWER_MASK);
                mt[kk] = mt[kk+M] ^ (y >> 1) ^ mag01[y & 0x1UL];
            }
            for (; kk<N-1; kk++) {
                y = (mt[kk]&UPPER_MASK)|(mt[kk+1]&LOWER_MASK);
                mt[kk] = mt[kk+(M-N)] ^ (y >> 1) ^ mag01[y & 0x1UL];
            }
            y = (mt[N-1]&UPPER_MASK)|(mt[0]&LOWER_MASK);
            mt[N-1] = mt[M-1] ^ (y >> 1) ^ mag01[y & 0x1UL];

            mti = 0;
        }

        y = mt[mti++];
        y ^= (y >> 11);
        y ^= (y << 7) & 0x9d2c5680UL;
        y ^= (y << 15) & 0xefc60000UL;
        y ^= (y >> 18);
        return y;
    }


    double Real(void)
    {
        return Int32()*(1.0/4294967295.0);
    }
    void PrintIt(unsigned int n) {
        char unsigned bytes[4];
        bytes[0] = (((n >> 24) & 0xFF) >> 1);
        bytes[1] = (((n >> 16) & 0xFF) >> 1);
        bytes[2] = (((n >> 8) & 0xFF) >> 1);
        bytes[3] = (n & 0xFF) >> 1;
        printf("%c%c%c%c",bytes[0],bytes[1],bytes[2],bytes[3]);
    }



   void PrintItBinary(unsigned int n) {
        for (short i =0; i < 32; i++)
            std::cout << ((n >> i) & 1);
    }


    // Generuje n bitowy ciag
    void Sequence(int n, bool binary = true) {
        // Podziel przez 32 bity
        for (int i = 0; i < n / 32 + 1; i++) {
            if (binary)
                PrintItBinary(Int32());
            else
                PrintIt(Int32());
        }
    }

};


void Wydatki(int count, int budzet = 1){
    MersenneTwister *mt = new MersenneTwister(1432);
	while (count-- > 0)
	{
		int produkt = mt->Int32()%99;
		float kwota = (float)(mt->Int32()%10001)/100.0;
		int m = 1 + mt->Int32()%12;
		int d = 1 + mt->Int32()%30;
		int r = 2012 + mt->Int32()%2;
		printf("INSERT INTO `Wydatki`(`ID_Budzetu`, `ID_Produktu`, `data`, `kwota`) VALUES ('%d','%d','%d-%d-%d','%.2f');\n",budzet,produkt,r,m,d,kwota);
	}
}

void Przychody(int count, int budzet = 1){
    MersenneTwister *mt = new MersenneTwister(123832);
	while (count-- > 0)
	{
		int produkt = 1+mt->Int32()%19;
		int numer = mt->Int32()%51250;
		float kwota = (float)(mt->Int32()%603401)/200.0;
		int m = 1 + mt->Int32()%12;
		int d = 1 + mt->Int32()%30;
		int r = 2012 + mt->Int32()%2;
		printf("INSERT INTO `Przychody` (`ID_Budzetu`, `ID_KatPrzychodu`,`nazwa`, `data`, `kwota`) VALUES ('%d','%d','Przelew numer #%d','%d-%d-%d','%.2f');\n",budzet,produkt,numer,r,m,d,kwota);
	}
}

int main(void)
{
    Wydatki(4000);
	//Przychody(34);

	return 0;

}
