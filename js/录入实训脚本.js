let excel = `刘俊强	88
欧阳志鹏	75
古晋荣	76
王书恒	87
谢竹根	77
杨学良	75
曾锦	82
李友晓	84
刘火军	85
李涛	89
施耀慧	84
吴园杰	88
黄家康	78
黄懋铖	74
李文靖	71
李振宇	89
龚安发	76
邱宇涛	85
钟广生	0
李钱陈	82
邹翔宇	74
邱龙华	82
李杰	76
吴向辉	76
刘坤	80
肖伊明	89
李梓良	89
潘翔	90
邱英波	85
朱建城	78
杨经棂	88
严朝灯	94
钟健	88
康里生	86
熊梓文	71
丁堃	77
谢金龙	80
郭振斌	77
刘超	79
罗来正	80
甘肃	92
蔡健华	82
黄灿	72
彭锦涛	80
曾子轩	77
罗河斌	87
杨旻杰	85
袁海裕	79
康晨	79
张思艺	81
彭霞	78
蔡孜艺	77
刘芳婷	79
吴天宝	91
卢佳健	90

`.trim().split("\n");
let COLUMN_NUM = 1;
let names = [];
let lines = [];
excel.forEach((line, index, arr) => {
    if (line.split('\t').length !== (COLUMN_NUM + 1)) {
        alert(`要求有${(COLUMN_NUM + 1)}列`);
        throw new Error(`要求有${(COLUMN_NUM + 1)}列！`);
    }
    names.push(line.substring(0, line.indexOf('\t')));
    lines.push(line.substring(line.indexOf('\t')).trim());
});
let nameMap = {};//<名字,对应names数组下标>
let nameEles = document.querySelectorAll("input[name='b']");
if (lines[0].split('\t').length !== COLUMN_NUM) {
    alert(`要求有${COLUMN_NUM}列成绩`);
    throw new Error(`要求有${COLUMN_NUM}列成绩！`);
}
let lackArr = []
nameEles.forEach((nameEle) => {
    nameMap[nameEle.value] = names.indexOf(nameEle.value);
    if (nameMap[nameEle.value] === -1) {
        lackArr.push(nameEle.value);
        nameEle.style.color = 'red';
        nameEle.style.backgroundColor = 'yellow';
        names.push(nameEle.value);
        lines.push(`0`);
        nameMap[nameEle.value] = names.indexOf(nameEle.value);
    }
})
if (lackArr.length) {
    alert(`这些人没有上传成绩：${lackArr},默认给零分并标红了`);
}
let arr1 = Object.keys(nameMap);
let arr2 = [];
for (let i = 0, l = names.length; i < l; i++) {
    if (!~arr1.indexOf(names[i])) {
        arr2.push(names[i]);
    }
}
if (arr2.length) {
    alert(`页面中找不到这些名字，请检查名字是否有误！：${arr2}`)
    throw new Error(`页面中没有这些名字：${arr2}`);
}
let scoreArrs = new Array(4);
scoreArrs[0] = [];
lines.forEach((line, i) => {
    let cols = lines[i].split('\t');
    cols.forEach((col, j) => {
        if (!col) {
            alert(`第${i}行成绩有误`)
        }
        scoreArrs[j].push(col);
    })
})
let cs = new Array(1);
cs[0] = document.querySelectorAll("input[name='g']");
scoreArrs.forEach((scoreArr, col) => {
    scoreArr.forEach((value, row) => {
        let index = nameMap[nameEles[row].value];
        if (index !== -1) {
            cs[col][row].value = scoreArr[index];
        } else {
            alert("没有给成绩！")
        }
    })
})