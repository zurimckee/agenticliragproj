#include <iostream>;
using namespace std;

int out_of_order_pairs(int *a, int n){
    int i, p, j;
    p = 0;
    for(i=0;i<n-1;i++){ 
       for(j=i+1;j<n;j++){ 
        if(a[i] > a[j]) p+=1;
       }
    }
    return p; 
}


