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
    clone.querySelectorAll("input:not(.qty):not(.order)")
        .forEach(function (el) {
            el.value = "";
        });
    clone.querySelector(".hiddenItem").value = "1";
    // clone.querySelector(".datalist").value = "1";
    clone.querySelector(".btn-primary").style.display = "inline";
    clone.querySelector("input.qty").value = "1";
    clone.querySelector("span.price").innerHTML = "Цена";
    clone.querySelector("input.price").value = "0";
    let toDelete = clone.querySelector(".deleteId");
    if (toDelete) {
        clone.querySelector(".deleteId").value = "0";
    }
    elem.after(clone);
    clone.querySelector(".btn-danger")
        .setAttribute("onclick", "javascript: removeItemList(this);");
    revaluateItemId();
    hideAndDisplayDatalistButtons();
    addListeners();
    }

function removeItemList(node) {
    let datalistField = node.parentNode.parentNode;
    datalistField.remove();
    revaluateItemId();
    hideAndDisplayDatalistButtons();
}

function duplicateRow(node) {
    let element = node.parentNode.parentNode.parentNode.parentNode.parentNode;
    orderSection.querySelector(".qty").value = "1";
    element.parentNode.appendChild(orderSection);
    //toggleDeviceSection(orderSection.querySelector(".toggle-button"));
    orderSection = orderSection.cloneNode(true);
    revaluateRowId();
    hideAndDisplayRowButtons();
    addListeners();
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

    if (deviceList.style.display === 'none') {
        deviceList.style = '';
        newDevice.style.display = 'none';
        return
    }
    if (newDevice.style.display === 'none') {
        newDevice.style = '';
        deviceList.style.display = 'none';
    }
}

function clean(node) {
    node.querySelectorAll(".toClean")
        .forEach(function (item) {
            item.value = "";
        });
    let datalists = node.querySelectorAll(".item-datalist");
    if (datalists.length != 0) {
        datalists[0].querySelector("input").value = "";
    }
    for (let i = 1; i < datalists.length; i++) {
        datalists[i].remove();
    }
}

function exchange(node) {
    let papa = node.parentNode.parentNode;
    let select = papa.querySelector("input.datalist");
    let selectPrice = papa.querySelector("span.input-group-text")
    let input = papa.querySelector("input.form-control.input");
    let inputPrice = papa.querySelector("input.form-control.price");

    if (select.style.display === "none") {
        select.style.display = "inline";
        selectPrice.style.display = "block";
        input.style.display = "none";
        inputPrice.style.display = "none";
    } else {
        select.style.display = "none";
        selectPrice.style.display = "none";
        input.style.display = "inline";
        inputPrice.style.display = "inline";
    }
    calculatePrice();
}

function saveOrder() {
    let r = confirm("Сохранить заказ?");
    if (r === true) {
        switchHiddenValues();
        removeHidden();
        document.getElementById('form').submit();
    }
}

function ordersFindClient() {
    let form = document.getElementById('form');
    form.setAttribute('action', '/orders/findByPhoneNumber');
    switchHiddenValues();
    removeHidden();
    form.submit();
}

function switchHiddenValues() {
    let dataListOptions = document.querySelectorAll('[hiddenValue]');
    for (let el of dataListOptions) {
        el.setAttribute("value", el.getAttribute("hiddenvalue"));
    }
}

function deleteWorkById(url) {
    // var url = document.getElementById('id_Элемента');
    let r = confirm("Удалить работу?");
    if (r === true) {
        document.location.href = url;
    }
}

function addListeners() {
    let inputs = document.querySelectorAll('input[list]');
    for (let el of inputs) {
        el.addEventListener('input', function (e) {
            let input = e.target,
                list = input.getAttribute('list'),
                options = document.querySelectorAll('#' + list + ' option'),
                hiddenInput = this.nextElementSibling,
                inputValue = input.value,
                price = this.parentNode.querySelector("span.input-group-text");

            hiddenInput.value = inputValue;

            for (var i = 0; i < options.length; i++) {
                var option = options[i];

                if (option.value === inputValue) {
                    hiddenInput.value = option.getAttribute('data-value');
                    price.innerHTML = option.getAttribute("price");
                    calculatePrice();
                    break;
                }
                hiddenInput.value = 0;
            }
        });
    }
}

function loadItemsFromHidden() {
    let hiddenInputs = document.querySelectorAll(".hiddenItem");
    let options = document.querySelectorAll("#datalistOptions option");
    if (!hiddenInputs[0].value) {
        return;
    }
    for (let input of hiddenInputs) {
        for (let i = 0; i < options.length; i++) {
            let option = options[i];
            if (input.value === option.getAttribute('data-value')) {
                input.previousElementSibling.value
                    = option.getAttribute('value');
                break;
            }
        }
    }
}

function removeHidden() {
    let elements = document.querySelectorAll("[style]");
    for (let el of elements) {
        if (el.style.display === 'none') {
            el.remove();
        }
    }
}

function calculatePrice() {
    let prices = document.querySelectorAll(".price")
    let sum = 0;
    for (const el of prices) {
        if (el.style.display !== 'none') {
            let qty = parseInt(el.parentNode.querySelector(".qty").value);
            if (el.innerHTML === "") {
                sum += parseInt(el.value) * qty;
            } else {
                if (!isNaN(parseInt(el.innerHTML))) {
                    sum += parseInt(el.innerHTML) * qty;
                }
            }
        }
    }
    document.querySelector(".price-text-sum").innerHTML = sum + ' руб.';
    document.querySelector(".price-text-sum-hidden").value = "" + sum;
}




















