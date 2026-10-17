#include <iostream>
using namespace std;



string binary_to_hex(string b){
    int i, bit, j;
    string final = "";
    while (b.length() % 4 != 0){ //pads with leading zeros to make it divisible by 4
        b.insert(0, "0");
    }

    for(i=0;i<b.length();i += 4){
        string smallb = b.substr(i, 4);
        int total = 0;
        for(j=0;j<smallb.length();j++){
            string result = " ";
            bit = smallb[j] - '0';
            total = total * 2 + bit; //converted to binary
        }
        char hex;
        if (total <= 9) hex = '0' + total;
        else hex = 'A' + (total-10);

        final += hex;
    }

    int pos = final.find_first_not_of('0');
    if(pos == string::npos) return "0";
    return final.substr(pos);


}

int max_two_product(int *a, int n){
    int i, hi1,hi2,lo1,lo2;

    if (a[0] > a[1]) hi1 = a[0], hi2=a[1]; 
    else hi1 = a[1], hi2=a[0];

    if(a[0] < a[1]) lo1=a[0], lo2=a[1]; 
    else lo1=a[1], lo2=a[0];

    for (i=2;i<n;i++){
        if ( a[i] > hi1) hi2 = hi1, hi1 = a[i]; //if element is larger than highest, highest becomes 2nd highest, 
        //a[i] becomes highest 
        else if (a[i] > hi2) hi2 = a[i]; //else if element larger than 2nd highest, element becomes 2nd highest

        if (a[i] < lo1) lo2 = lo1, lo1 = a[i]; // if element is smaller than lowest, lowest becomes 2nd lowest, 
        //element becomes lowest
        else if (a[i] < lo2) lo2 = a[i];
        //else if element is smaller than 2nd lowest, element becomes 2nd lowest
    }
    int max = hi1 * hi2; //finds the max prod with the hi nums
    int neg_max = lo1 *  lo2; //finds max prod with lo nums

    if ((max) > (neg_max)) return max; //returns max prod between both groups
    else return neg_max;
   
    
}
