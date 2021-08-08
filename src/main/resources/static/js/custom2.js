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
        let items = row.querySelectorAll(".item-datalist:not(.parts)");
        process(items, 'listOfServices');
        let parts = row.querySelectorAll(".item-datalist.parts");
        process(parts, 'listOfParts');
    });

    function process(items, selector) {
        for (let i = 0; i < items.length; i++) {
            items[i]
                .querySelectorAll(".itemIdGen")
                .forEach(function (el) {
                    let splitId = el.id.split(".");
                    for (let j = 0; j < splitId.length; j++) {
                        if (splitId[j].includes(selector)) {
                            splitId[j] = `${selector}${i}`;
                            el.id = splitId.join(".");
                        }
                    }
                    let splitName = el.name.split(".");
                    for (let j = 0; j < splitName.length; j++) {
                        if (splitName[j].includes(selector)) {
                            splitName[j] = `${selector}[${i}]`;
                            el.name = splitName.join(".");
                        }
                    }
                });
        }
    }
}

function duplicateItemList(node, isPart) {
    let elem = node.parentNode.parentNode;
    let clone = elem.cloneNode(true);
    clone.querySelectorAll("input:not(.qty):not(.order):not(.checkbox)")
        .forEach(function (el) {
            el.value = "";
        });
    clone.querySelector(".hiddenItem").value = "0";
    // clone.querySelector(".datalist").value = "1";
    if (!isPart) {
        clone.querySelector(".btn-primary").style.display = "inline";
        clone.querySelector("span.price").innerHTML = "Цена";
        clone.querySelector("input.price").value = "0";
    } else {
        clone.querySelector("span.partPrice").innerHTML = "Цена";
        clone.querySelector("input.partPrice").value = "0.0";
    }
    clone.querySelector("input.qty").value = "1";
    let toDelete = clone.querySelector(".deleteId");
    if (toDelete) {
        clone.querySelector(".deleteId").value = "0";
    }
    // clone.querySelector(".checkbox").value = true;
    // clone.querySelector(".checkbox").nextElementSibling.value = "on";
    elem.after(clone);
    clone.querySelector(".btn-danger")
        .setAttribute("onclick",
            "javascript: removeItemList(this," + isPart + ");");
    revaluateItemId();
    hideAndDisplayDatalistButtons(isPart);
    calculatePrice(node);
    calculatePartsPrice(node);
    addListeners();
    }

function removeItemList(node, isPart) {
    let datalistField = node.parentNode.parentNode;
    datalistField.remove();
    revaluateItemId();
    hideAndDisplayDatalistButtons(isPart);
}

function duplicateRow(node) {
    let element = node.parentNode.parentNode.parentNode.parentNode.parentNode;
    orderSection.querySelectorAll(".qty").forEach(el => el.value = "1");
    orderSection.querySelector("input.partPrice").value = "0.0"
    element.parentNode.appendChild(orderSection);
    //toggleDeviceSection(orderSection.querySelector(".toggle-button"));
    orderSection = orderSection.cloneNode(true);
    revaluateRowId();
    hideAndDisplayRowButtons(true);
    hideAndDisplayRowButtons(false);
    addListeners();
}

function removeRow(node) {
    node.parentNode.parentNode.parentNode.parentNode.parentNode.remove();
    revaluateRowId();
    hideAndDisplayRowButtons(true);
    hideAndDisplayRowButtons(false);
}

function hideAndDisplayDatalistButtons(isPart) {
    let rowButtons = document.querySelectorAll(".row.sectionRow");
    rowButtons.forEach(function (row) {
        process(row, isPart);
    });

    function process(item,  isPart) {
        let selector = isPart ? ".item-datalist.parts" : ".item-datalist:not(.parts)";
        let listOfDatalists = item.querySelectorAll(selector);
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
        row.querySelector(".addRowButton").style.display = "none";
        row.querySelector(".removeRowButton").style.display = "inline";
    });

    rowButtons[rowButtons.length - 1]
        .querySelector(".addRowButton").style.display = "inline";
    rowButtons[rowButtons.length - 1]
        .querySelector(".removeRowButton").style.display = "inline";

    if (rowButtons.length === 1) {
        rowButtons[0].querySelector(".removeRowButton")
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
    let datalists = node.querySelectorAll(".item-datalist:not(.parts)");
    if (datalists.length != 0) {
        datalists[0].querySelector("input").value = "";
    }
    for (let i = 1; i < datalists.length; i++) {
        datalists[i].remove();
    }
    let parts = node.querySelectorAll(".item-datalist.parts");
    if (parts.length != 0) {
        parts[0].querySelector("input").value = "";

    }
    for (let i = 1; i < parts.length; i++) {
        parts[i].remove();
    }

}

function exchange(node) {
    let papa = node.parentNode.parentNode;
    let select = papa.querySelector("input.datalist");
    let selectPrice = papa.querySelector("span.input-group-text")
    let input = papa.querySelector("input.form-control.input");
    let inputPrice = papa.querySelector("input.form-control.price");
    let price2 = papa.querySelector("input.form-control.price2");
    let partPrice = papa.querySelector("input.form-control.partPrice");
    let checkBox = papa.querySelector("input.checkbox")
    let extra = papa.querySelector("select.extra")

    if (select.style.display === "none") {
        select.style.display = "inline";
        selectPrice.style.display = "block";
        input.style.display = "none";
        if (price2)
            price2.style.display = "none";
        if (partPrice)
            partPrice.style.display = "none";
        if (inputPrice)
            inputPrice.style.display = "none";
        if (extra)
            extra.style.display = "none";
    } else {
        select.style.display = "none";
        selectPrice.style.display = "none";
        input.style.display = "inline";
        if (price2)
            price2.style.display = "inline";
        if (partPrice)
            partPrice.style.display = "inline";
        if (inputPrice)
            inputPrice.style.display = "inline";
        if (extra)
            extra.style.display = "inline";
    }
    if (checkBox.getAttribute("checked") == "checked") {
        checkBox.removeAttribute("checked");
        checkBox.value = false;
    } else {
        checkBox.setAttribute("checked", "checked");
        checkBox.value = true;
    }

    calculatePrice(node);
}

function saveOrder() {
    let r = confirm("Сохранить заказ?");
    if (r === true) {
        switchHiddenValues();
        removeHidden();
        document.getElementById('form').submit();
    }
}

function submitForm() {
    let r = confirm("Сохранить изменения?");
    if (r === true) {
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

function selectClient() {
    let form = document.getElementById('form');
    form.setAttribute('action', '/orders/findClient');
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
    let r = confirm("Удалить?");
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
                    if (list.includes("part")) {
                        calculatePartsPrice(input);
                    } else {
                        calculatePrice(input);
                    }
                    break;
                }
                hiddenInput.value = 0;
            }
        });
    }
    let formControl = document.querySelector(".form-control.phoneNumber");
    if (formControl) {
        formControl.addEventListener('keydown', (e) => {
            if (e.key === "Enter") {
                ordersFindClient();
            }
        });
    }

}

function loadItemsFromHidden() {
    loadItems(".hiddenItem:not(.part)", "#datalistOptions option");
    loadItems(".hiddenItem.part", "#partListOptions option");
}

function loadItems(hidden, id) {

    let hiddenInputs = document.querySelectorAll(hidden);
    let options = document.querySelectorAll(id);
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
        if (el.style.display === 'none' && !el.className.includes("checkbox")) {
            el.remove();
        }
    }
}

function calculatePrice(node) {
    let prices = node.parentNode.parentNode.parentNode.querySelectorAll(".price")
    let sum = 0;
    for (const el of prices) {
        if (el.style.display !== 'none') {
            let qty = parseInt(el.parentNode.querySelector(".qty").value);
            if (el.innerHTML === "") {
                if (!isNaN(parseInt(el.value))) {
                    sum += parseInt(el.value) * qty;
                }
            } else {
                if (!isNaN(parseInt(el.innerHTML))) {
                    sum += parseInt(el.innerHTML) * qty;
                }
            }
        }
    }
    if (document.querySelector(".discount-of-order")) {
        sum -= sum / 100 * parseInt(document.querySelector(".discount-of-order").innerHTML);
    }
    node.parentNode.parentNode.parentNode.querySelector(".price-text-sum").innerHTML = sum + ' руб.';
    calculateSum();
}

function calculatePartsPrice(node) {
    let prices = node.parentNode.parentNode.parentNode.querySelectorAll(".partPrice")
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
    node.parentNode.parentNode.parentNode.querySelector(".price-parts-text-sum").innerHTML = sum + ' руб.';
    calculateSum();
}

function calculateSum() {
    let prices = document.querySelectorAll(".calculate");
    let sum = 0;
    for (const el of prices) {
        let price = parseInt(el.innerHTML);
        if (!isNaN(price)) {
            sum += price;
        }
    }
    document.querySelector(".total-sum").innerHTML = sum + ' руб.';

}

function setExecutor(node) {
    let value = node.value;
    let selects = document.querySelectorAll(".executor-select");
    for (let i = 0; i < selects.length; i++) {
        selects[i].value = value;
    }
}

function changePrice(node, isPart) {
    let papa = node.parentNode;
    let qty = parseInt(papa.querySelector(".qty").value);
    if (isNaN(qty)) qty = 0;
    let purchasePrice = parseFloat(papa.querySelector(".price2").value);
    if (isNaN(purchasePrice)) purchasePrice = 0;
    let extra = parseInt(papa.querySelector(".extra").value);
    if (isNaN(extra)) extra = 0;
    let sumField = papa.querySelector(".partPrice.custom");
    let sum = purchasePrice * (1 + 1 / 100 * extra);
    sumField.value = sum;
    sumField.setAttribute("value", sum);
    if (isPart) {
        calculatePartsPrice(node);
    } else {
        calculatePrice(node);
    }
}

function addExtra() {
    let purchase = parseInt(document.querySelector(".purchase").value),
        extra = parseInt(document.querySelector(".stockExtra").value),
        price = document.querySelector(".stockPrice");

    if (isNaN(extra)) {
        extra = 0;
    }
    let fullPrice = purchase + (purchase / 100 * extra);
    price.value = (fullPrice | 0);
}



















