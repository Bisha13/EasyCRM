function revaluateRowId() {
    let rows = document.querySelectorAll(".row.sectionRow");
    for (let i = 0; i < rows.length; i++) {
        let items = rows[i].querySelectorAll(".rowIdGen");
        process(items, i);
    }

    function process(items, index) {
        items.forEach(function (item) {
            let splitId = item.id.split(".");
            if (splitId[0].includes("orderList")) {
                splitId[0] = `orderList${index}`;
                item.id = splitId.join(".");
            }
            let partsOfName = item.name.split(".");
            if (partsOfName[0].includes("orderList")) {
                partsOfName[0] = `orderList[${index}]`;
                item.name = partsOfName.join(".");
            }
        });
    }
}

function revaluateItemId() {
    let rows = document.querySelectorAll(".row.sectionRow");
    rows.forEach(function (row) {
        let items = row.querySelectorAll(".item-datalist");
        process(items);
    });


    function process(items) {
        for (let i = 0; i < items.length; i++) {
            items[i]
                .querySelectorAll(".itemIdGen")
                .forEach(function (el) {
                    let splitId = el.id.split(".");
                    for (let j = 0; j < splitId.length; j++) {
                        if (splitId[j].includes("listOfWorks")) {
                            splitId[j] = `listOfWorks${i}`;
                            el.id = splitId.join(".");
                        }
                    }
                    let splitName = el.name.split(".");
                    for (let j = 0; j < splitName.length; j++) {
                        if (splitName[j].includes("listOfWorks")) {
                            splitName[j] = `listOfWorks[${i}]`;
                            el.name = splitName.join(".");
                        }
                    }
                });
        }
    }
}

function duplicateItemList(node) {
    var elem = node.parentNode.parentNode;
    var clone = elem.cloneNode(true);
    clone.querySelector("select").value = "169";
    clone.querySelector(".deleteId").value = "0";
    elem.after(clone);
    revaluateItemId();
    hideAndDisplayDatalistButtons();
}

function removeItemList(node) {
    let datalistField = node.parentNode.parentNode;
    datalistField.remove();
    revaluateItemId();
    hideAndDisplayDatalistButtons();
}

function duplicateRow(node) {
    let element = node.parentNode.parentNode.parentNode.parentNode.parentNode;
    element.parentNode.appendChild(section);
    section = section.cloneNode(true);
    revaluateRowId();
    hideAndDisplayRowButtons();
}

function removeRow(node) {
    node.parentNode.parentNode.parentNode.parentNode.parentNode.remove();
    revaluateRowId();
    hideAndDisplayRowButtons();
}

function hideAndDisplayDatalistButtons() {
    let rowButtons = document.querySelectorAll(".row");
    rowButtons.forEach(function (row) {
        process(row);
    });

    function process(item) {
        let listOfDatalists = item.querySelectorAll(".item-datalist");
        listOfDatalists.forEach(function (list) {
            list.querySelector(".btn-success").style.display = "none";
            list.querySelector(".btn-danger").style.display = "inline";
        });
        listOfDatalists[listOfDatalists.length - 1]
            .querySelector(".btn-success").style.display = "inline";
        listOfDatalists[listOfDatalists.length - 1]
            .querySelector(".btn-danger").style.display = "inline";
        if (listOfDatalists.length === 1) {
            listOfDatalists[listOfDatalists.length - 1]
                .querySelector(".btn-danger").style.display = "none";
        }
    }
}

function hideAndDisplayRowButtons() {
    let rowButtons = document.querySelectorAll(".row.sectionRow");

    rowButtons.forEach(function (row) {
        row.querySelector(".btn-primary").style.display = "none";
        row.querySelector(".btn-secondary").style.display = "inline";
    });

    rowButtons[rowButtons.length - 1]
        .querySelector(".btn-primary").style.display = "inline";
    rowButtons[rowButtons.length - 1]
        .querySelector(".btn-secondary").style.display = "inline";

    if (rowButtons.length === 1) {
        rowButtons[0].querySelector(".btn-secondary")
            .style.display = "none";
    }
}

function saveSection() {
    section = document.getElementsByClassName("row")[0].cloneNode(true);
    clean(section);
}

function clean(node) {
    node.querySelectorAll(".toClean")
        .forEach(function (item) {
            item.value = "";
        });
    let datalists = node.querySelectorAll(".item-datalist");
    datalists[0].querySelector("select").value = "169";
    for (let i = 1; i < datalists.length; i++) {
        datalists[i].remove();
    }
}
function deleteWorkById(url) {
    // var url = document.getElementById('id_Элемента');
    let r = confirm("Удалить работу?");
    if (r === true) {
        document.location.href = url;
    }
}