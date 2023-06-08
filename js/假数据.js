function ran(init = 0, len = 10, times = 10, maxDiv = 10) {
  let arr = new Array(len);
  for (let i = 0; i < len; i++) {
    arr[i] = init;
  }
  if(init>0){ 
    for (let i = 0; i < times; i++) {
      let index1 = parseInt(Math.random() * len);
      let index2 = parseInt(Math.random() * len);
      if (Math.abs(arr[index1] - init) < maxDiv
        && Math.abs(arr[index2] - init) < maxDiv) {
			if(arr[index1]-1>=0 && arr[index2]+1<=100){
        arr[index1]--;
        arr[index2]++;
			}
      } else {
        i--;
      }
    }
  }
  return arr;
}
function ranArr(arr = [60, 61, 62, 63, 64, 65], len = 10, times = 10, maxDiv = 10) {
  let ret = [];
  for (let i = 0; i < arr.length; i++) {
    ret.push(ran(arr[i], len, times, maxDiv).join("\t"));
  }
  return ret;
}
function test(){
  let arr = `
  86 
71 
50 
80 
71 
57 
66 
67 
79 
66 
70 
100 
64 
66 
0 
46 
43 
57 
0 
71 
71 
66 
0 
60 
0 
0 
64 
0 
43 
79 
46 
77 
0 
43 
77 
0 
64 
66 
80 
69 
93 
77 
0 
47 

    
`.trim().split("\n");
arr.forEach((d,index)=>{ arr[index] = parseInt(arr[index].trim()) })
console.log(ranArr(arr,10,10,5).join("\n"));
}

test();