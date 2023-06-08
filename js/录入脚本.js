let excel = `黄彪	95	82	83	22
祝剑国	98	75	85	74
曾凯	100	95	90	55
李超辉	100	90	93	17
程永清	85	82	78	26
樊云飞	97	80	76	45
万骏	100	87	85	64
李晨稳	90	84	82	37
阙志勇	90	72	78	34
杨敏	95	82	78	8
刘喆	100	74	76	53
边涛	65	65	60	26
黄然	100	77	76	32
张佳城	95	82	84	14
魏梦超	90	83	89	30
徐诚文	92	82	73	26
徐强	100	76	78	3
赵旭龙	100	77	80	20
邬龙飞	95	71	72	30
罗诚	100	93	96	85
项永军	95	85	83	18
许奇	90	80	74	55
邓星宇	100	72	81	12
徐赣南	100	78	75	56
宋泽林	100	77	76	71
钟根法	100	77	78	66
许淋昆	100	80	81	56
钟佳朋	100	78	83	69
刘坤	100	77	78	36
张子豪	100	73	87	33
杨海洋	90	78	75	20
杨家宝	95	75	80	12
温广林	100	82	81	16
杨根长	100	77	75	30
江青山	95	79	78	44
陈卓平	100	74	80	17
池含稳	95	78	85	24
朱杰平	100	87	85	33
孙康麟	100	85	87	14
陈富强	100	79	75	41
刘景涛	90	82	78	29
王梓豪	90	72	80	19
钟紫豪	100	82	83	50
齐健强	100	82	85	11
方家豪	100	76	73	28
张允阳	100	76	80	17
胡虹霞	100	76	78	38
钟丽华	100	90	93	64
李小芳	95	82	86	20
赖诗	100	95	97	82
陈燕	100	87	89	66
周丽平	100	89	85	42
郑乐峰	100	89	83	66
胡志伟	100	87	82	17
俞桐	65	63	56	22






`.trim().split("\n");
let COLUMN_NUM = 4;
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
        lines.push(`0	0	0	0`);
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
scoreArrs[1] = [];
scoreArrs[2] = [];
scoreArrs[3] = [];
lines.forEach((line, i) => {
    let cols = lines[i].split('\t');
    cols.forEach((col, j) => {
        if (!col) {
            alert(`第${i}行成绩有误`)
        }
        scoreArrs[j].push(col);
    })
})
let cs = new Array(4);
cs[0] = document.querySelectorAll("input[name='c']");
cs[1] = document.querySelectorAll("input[name='d']");
cs[2] = document.querySelectorAll("input[name='e']");
cs[3] = document.querySelectorAll("input[name='h']");
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