import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ProductModel, ItemDetailTypeByCategory } from '../../product.types';
import { ProductService } from '../../product.service';
import { numberPattern } from '@shared/patterns.constants';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss'],
})
export class AddProductComponent implements OnInit {
  constructor(private fb: FormBuilder, private _service: ProductService) {
    this._createForm();
    this._getAllCategory();
    this.itemDetailsType = [];
    this.itemDetailsType[0] = [];
    this.selectedItemDetails = [];
  }

  ngOnInit(): void {}

  addProductForm: FormGroup;
  itemDetailsForm: FormGroup;
  isLoading = false;
  categories: any;
  subCategoriesList: any[];
  itemDetailsType: any[];
  selectedCategoryId: number;
  selectedCategory: any;
  selectedItemDetails: string[];

  _getAllCategory() {
    this._service.getAllCategory().subscribe((res) => {
      this.categories = res.items;
    });
  }

  onCategoryChange(id: number) {
    if (this.categories) {
      const data = this.categories.filter((i: any) => i.id === id);
      this.selectedCategory = data[0];
      this.itemDetailsType[0] = [];
      this.subCategoriesList = [
        ...data[0].subCategories.map((c: any) => {
          return { ...c, name: c.name.replace(/_/g, ' ') };
        }),
      ];
    }
  }

  _createForm() {
    this.addProductForm = this.fb.group({
      name: ['', { validators: [Validators.required, Validators.minLength(5)] }],
      qwt: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      price: ['', { validators: [Validators.required, Validators.pattern('^[0-9]+.[0-9]{2,4}$')] }],
      category: ['', { validators: [Validators.required] }],
      subCategory: ['', { validators: [Validators.required] }],
      available: [false, { validators: [] }],
      details: new FormArray([]),
    });
  }

  get name() {
    return this.addProductForm.get('name');
  }
  get qwt() {
    return this.addProductForm.get('qwt');
  }
  get price() {
    return this.addProductForm.get('price');
  }
  get subCategory() {
    return this.addProductForm.get('subCategory');
  }
  get category() {
    return this.addProductForm.get('category');
  }
  get available() {
    return this.addProductForm.get('available');
  }

  get f() {
    return this.addProductForm.controls;
  }
  get t() {
    return this.f.details as FormArray;
  }

  onProductAdd(values: ProductModel) {
    this.isLoading = true;
    console.log(values);
    this._service.addProduct(values).subscribe((res) => {
      this.isLoading = false;
      this.addProductForm.markAsPristine();
      this._createForm();
    });
  }

  filterItemDetailsValue(ev: any, value: any, index: number) {
    this.itemDetailsType.forEach((item: any, i: number) => {
      console.log('i is {} {}==>', i, ev.value);
      if (i !== index && ev.value) {
        console.log(item.filter((i: string) => i != ev.value));
        this.itemDetailsType[i] = item.filter((i: string) => i != ev.value);
      }
    });
  }

  onItemDetailsChange(ev: any, controls: any) {
    if (this.selectedItemDetails.indexOf(ev.value) === -1) {
      this.selectedItemDetails.push(ev.value);
    }
  }

  addMoreDetails(index: number, controls: any) {
    let cats = ItemDetailTypeByCategory[this.selectedCategory.name.toLowerCase()];
    this.itemDetailsType[index + 1] = cats;
    this._addItemToForm();
  }

  addMoreItemDetails(value: any) {
    let cats = ItemDetailTypeByCategory[this.selectedCategory.name.toLowerCase()];
    this.itemDetailsType[0] = cats;
    this._addItemToForm();
  }

  _addItemToForm() {
    this.t.push(
      this.fb.group({
        name: ['', Validators.required],
        value: ['', [Validators.required, Validators.pattern('^[0-9]+.[0-9]{2,4}$')]],
      })
    );
  }

  removeItemDetils(i: number, control: any) {
    this.t.removeAt(i);
    if (this.selectedItemDetails.indexOf(control.value.name) !== -1) {
      this.selectedItemDetails = this.selectedItemDetails.filter((i) => i !== control.value.name);
    }
    if (this.itemDetailsType.length !== 1) {
      this.itemDetailsType.splice(i, 1);
    } else {
      this.itemDetailsType[0] = [];
    }
  }
}
