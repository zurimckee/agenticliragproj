#include <iostream>
using namespace std;

int min(int a[], int n){
    int temp = 0;

    for(int i=1;i<n;i++){
        if(a[i]<a[temp]){
            i=temp;
        }
    }
    return a[temp];
    
}

int main(){
    int a[] = {3,10,1,4,-100, 2};
    int n = sizeof(a)/sizeof(int);
    cout << min(a,n) << endl;
    return 0;
}