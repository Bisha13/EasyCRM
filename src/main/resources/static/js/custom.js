var section;

function duplicate(node) {
    var elem = node.parentNode.parentNode;
    var clone = elem.cloneNode(true);
    elem.after(clone);
    // clone.querySelector(".form-control").value = "";
    // clone.querySelector(".form-control").focus;
    // clone.querySelector(".form-control").setActive;
    // clone.querySelector(".form-control").select();

    clone.querySelector(".btn-danger").style.display = 'inline';
    elem.querySelector(".btn-danger").style.display = 'inline';
    node.style.display = 'none';

    revaluateIds();
}

function remove(node) {
    let datalistField = node.parentNode.parentNode;
    let list = datalistField.parentNode;
    datalistField.remove();
    const fields = list.querySelectorAll(".item-datalist");

    if (fields[fields.length - 1].querySelector(".btn-success") != null) {
        fields[fields.length - 1].querySelector(".btn-success").style.display = "inline";
        if (fields.length == 1) {
            fields[0].querySelector(".btn-danger").style.display = "none";
        }
    }
    revaluateIds();
}

function saveSection() {
    section = document.getElementsByClassName("row")[0].cloneNode(true);
    revaluateIds();
}

function anotherOne(node) {
    let element = node.parentNode.parentNode.parentNode.parentNode.parentNode;
    element.querySelector(".btn-primary").style.display = "none";
    element.querySelector(".btn-secondary").style.display = "inline";
    section.querySelector(".btn-secondary").style.display = "inline";
    element.parentNode.appendChild(section);
    section = section.cloneNode(true);

    revaluateRowIds();
}

function removeRow(node) {
    node.parentNode.parentNode.parentNode.parentNode.parentNode.remove();

    let fields = document.querySelectorAll(".row.sectionRow");
    fields[fields.length - 1].querySelector(".btn-primary").style.display = "inline";
    if (fields.length == 1) {
        fields[0].querySelector(".btn-secondary").style.display = "none";
    }
    revaluateRowIds();
}

// var fieldList = document.getElementsByClassName("item-datalist");
// for (let el of fieldList) {
//   el.addEventListener("keydown", function (e) {
//     if (e.code === "Enter") {  //checks whether the pressed key is "Enter"
//       duplicate(this.firstChild);
//     }
// });
// }
function revaluateIds() {
    let dataInputs = document.querySelectorAll(".input-group.item-datalist");

    let i = 0;
    for (let el of dataInputs) {
        el.id = 'datalist-input-' + i;

        let select = el.querySelector(".form-control");

        let oldId = select.id;

        let rowIndex = oldId.split(".")[0].replace(/[a-zA-Z]+/, "");

        select.id = `orderList${rowIndex}.listOfWorks${i}.item`;
        select.name = `orderList[${rowIndex}].listOfWorks[${i}].item`;
        i++;
    }
}

function revaluateRowIds() {
    let sectionRows = document.querySelectorAll(".row.sectionRow");
    for (let i = 0; i < sectionRows.length; i++) {
        let innerClasses = sectionRows[i].querySelectorAll(".forRowIndGen");
        for (let el of innerClasses) {

            let partsOfId = el.id.split(".");
            if (partsOfId[0].includes("orderList")) {
                partsOfId[0] = `orderList${i}`;
                el.id = partsOfId.join(".");
            }

            let partsOfName = el.name.split(".");
            if (partsOfName[0].includes("orderList")) {
                partsOfName[0] = `orderList[${i}]`;
                el.name = partsOfName.join(".");
            }
        }
    }
}
