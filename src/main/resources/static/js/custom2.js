let orderSection;
let newDeviceSection;
let deviceListSection;

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
                        if (splitId[j].includes("listOfServices")) {
                            splitId[j] = `listOfServices${i}`;
                            el.id = splitId.join(".");
                        }
                    }
                    let splitName = el.name.split(".");
                    for (let j = 0; j < splitName.length; j++) {
                        if (splitName[j].includes("listOfServices")) {
                            splitName[j] = `listOfServices[${i}]`;
                            el.name = splitName.join(".");
                        }
                    }
                });
        }
    }
}

function duplicateItemList(node) {
    let elem = node.parentNode.parentNode;
    let clone = elem.cloneNode(true);
    clone.querySelector("select").value = "169";
    let toDelete = clone.querySelector(".deleteId");
    if (toDelete) {
        clone.querySelector(".deleteId").value = "0";
    }
    elem.after(clone);
    clone.querySelector(".btn-danger")
        .setAttribute("onclick", "javascript: removeItemList(this);");
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
    element.parentNode.appendChild(orderSection);
    toggleDeviceSection(orderSection.querySelector(".toggle-button"));
    orderSection = orderSection.cloneNode(true);
    revaluateRowId();
    hideAndDisplayRowButtons();
}

function removeRow(node) {
    node.parentNode.parentNode.parentNode.parentNode.parentNode.remove();
    revaluateRowId();
    hideAndDisplayRowButtons();
}

function hideAndDisplayDatalistButtons() {
    let rowButtons = document.querySelectorAll(".row.sectionRow");
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
        row.querySelector(".btn-primary.btn-lg").style.display = "none";
        row.querySelector(".btn-secondary.btn-lg").style.display = "inline";
    });

    rowButtons[rowButtons.length - 1]
        .querySelector(".btn-primary.btn-lg").style.display = "inline";
    rowButtons[rowButtons.length - 1]
        .querySelector(".btn-secondary.btn-lg").style.display = "inline";

    if (rowButtons.length === 1) {
        rowButtons[0].querySelector(".btn-secondary.btn-lg")
            .style.display = "none";
    }
}

function saveOrderSection() {
    orderSection = document.getElementsByClassName("row")[0].cloneNode(true);
    clean(orderSection);
}

function saveDeviceSection() {
    newDeviceSection = document
        .getElementsByClassName("new-device-section")[0]
        .cloneNode(true);
    clean(newDeviceSection);
    try {
        deviceListSection = document
            .getElementsByClassName("device-list-section")[0]
            .cloneNode(true);
    } catch (e) {
    }
}

function toggleDeviceSection(element) {
    let deviceList = element.parentNode.parentNode.parentNode
        .querySelector(".device-list-section");
    let newDevice = element.parentNode.parentNode.parentNode
        .querySelector(".new-device-section");

    if (deviceList != null && newDevice != null) {
        deviceList.parentNode.removeChild(deviceList);
        revaluateRowId();
        return;
    }
    if (newDevice != null && deviceListSection != null) {
        newDevice.parentNode.insertBefore(deviceListSection.cloneNode(true), newDevice);
        newDevice.parentNode.removeChild(newDevice);
        revaluateRowId();
        //rewaluateIds
        return;
    }
    if (deviceList != null) {
        deviceList.parentNode.insertBefore(newDeviceSection.cloneNode(true), deviceList);
        deviceList.parentNode.removeChild(deviceList);
        revaluateRowId();
    }
}

function clean(node) {
    node.querySelectorAll(".toClean")
        .forEach(function (item) {
            item.value = "";
        });
    let datalists = node.querySelectorAll(".item-datalist");
    if (datalists.length != 0) {
        datalists[0].querySelector("select").value = "169";
    }
    for (let i = 1; i < datalists.length; i++) {
        datalists[i].remove();
    }
}

function exchange(node) {
    let papa = node.parentNode.parentNode;
    let select = papa.querySelector("select.form-control");
    let input = papa.querySelector("input.form-control");

    if (select.style.display === "none") {
        select.style.display = "inline";
        input.style.display = "none";
    } else {
        select.style.display = "none";
        input.style.display = "inline";
    }
}

function deleteWorkById(url) {
    // var url = document.getElementById('id_Элемента');
    let r = confirm("Удалить работу?");
    if (r === true) {
        document.location.href = url;
    }
}