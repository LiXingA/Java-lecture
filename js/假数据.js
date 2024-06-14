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
  let arr = `60 
  60 
  95 
  96 
  78 
  80 
  80 
  80 
  58 
  60 
  95 
  59 
  65 
  0 
  59 
  70 
  57 
  82 
  57 
  59 
  96 
  0 
  88 
  93 
  90 
  97 
  90 
  86 
  65 
  70 
  65 
  75 
  59 
  58 
  0 
  0 
  91 
  61 
`.trim().split("\n");
arr.forEach((d,index)=>{ arr[index] = parseInt(arr[index].trim()) })
console.log(ranArr(arr,10,10,5).join("\n"));
}

test();