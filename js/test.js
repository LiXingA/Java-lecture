let sum = 0;
let count = 0;
let lackCount = -1;
let jige = 0;
document.querySelectorAll("tr>td:nth-child(8)").forEach((item, index) => {
    if (~["*0", "总评成绩"].indexOf(item.textContent)) {
        lackCount++;
        return;
    }
    if (~item.textContent.indexOf("*")) {
        jige++;
    }
    sum += parseFloat(item.textContent.replaceAll("*", ""));
    count++;
})
window.scrollTo(0, document.body.scrollHeight)
alert(`退学人数：${lackCount}\n 考试人数：${count}\n 平均分：${Number(sum / count).toFixed(0)}\n 及格：${count - jige}`);