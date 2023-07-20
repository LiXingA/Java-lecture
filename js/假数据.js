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
  30
46
44
76
46
87
97
44
61
16
44
87
61
74
79
71
16
44
23
9
61
61
44
76
73
37
79
44
44
44
44
44
44
87
86
44
46
44
31
31
44
33
21
23
46
34
71
79
87
46
79
79
46
53


    
`.trim().split("\n");
arr.forEach((d,index)=>{ arr[index] = parseInt(arr[index].trim()) })
console.log(ranArr(arr,10,10,5).join("\n"));
}

test();