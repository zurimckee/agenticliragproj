#include <iostream>
using namespace std;

void print(int a[], int n){
    int i;
    for(i=0;i<n;i++) cout << a[i] << " ";
    cout << endl;
}

int simple_array_sum(int a[], int n) {
    int count = 0;
    for(int i=0;i<n;i++){
        count += a[i];
    }
    
    return count;
}

int main(){
    int a[] = {3,4,10,5,4,3,1};
    int n = sizeof(a)/sizeof(int); // current number of elements in the array
    cout << simple_array_sum(a, n) << endl;

}
