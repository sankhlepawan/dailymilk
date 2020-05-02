import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { OrderModel } from '../../order.types';
import { OrderService } from '../../order.service';
import { ProductService } from '../../../product/product.service';
import { numberPattern } from '@shared/constants';

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.scss'],
})
export class CreateOrderComponent implements OnInit {
  constructor(private fb: FormBuilder, private _service: OrderService, private _productService: ProductService) {
    this._createForm();
    this.options = [];
    this.options[0] = [];
  }

  ngOnInit(): void {}

  addProductForm: FormGroup;
  itemDetailsForm: FormGroup;
  isLoading = false;
  categories: any;
  subCategoriesList: any[];
  selectedCategoryId: number;
  selectedCategory: any;
  options: any[];
  grandTotal: number = 0;

  _createForm() {
    this.addProductForm = this.fb.group({
      //   name: ['', { validators: [Validators.required, Validators.minLength(5)] }],
      //   qwt: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      //   price: ['', { validators: [Validators.required, Validators.pattern('^[0-9]+.[0-9]{2,4}$')] }],
      //   category: ['', { validators: [Validators.required] }],
      //   subCategory: ['', { validators: [Validators.required] }],
      //   available: [false, { validators: [] }],
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

  onProductAdd(values: OrderModel) {
    this.isLoading = true;
    console.log(values);
  }

  addMoreDetails(index: number, controls: any) {
    this.options[index + 1] = [];
    this._addItemToForm();
  }

  addMoreItemDetails(value: any) {
    this._addItemToForm();
  }

  displayFn(opt: any) {
    if (opt.name !== 'No Data found') {
      return opt.name;
    }
    return '';
  }

  setQwtAndPrice(item: any, control: any) {
    control.controls.price.setValue(item.price);
    control.controls.total.setValue(item.price);
    control.controls.qwt.setValue(1);
    this._calculateGrandTotal();
  }

  onInputChange(data: string, i: number, control: any) {
    this._resetControl(control);
    if (data && data.length > 3) {
      this._productService.getproductByName(data).subscribe((res) => {
        if (res.items.length !== 0) {
          this.options[i] = res.items;
        } else {
          this.options[i] = [{ name: 'No Data found' }];
        }
      });
    }
  }

  _addItemToForm() {
    this.options[0] = [];
    this.t.push(
      this.fb.group({
        name: ['', Validators.required],
        price: [{ value: '', disabled: true }],
        total: [{ value: '', disabled: true }],
        qwt: ['', [Validators.required]],
      })
    );
  }

  onQwtChange(qw: number, control: any, index: number) {
    control.controls.price.setValue(control.value.name.price);
    control.controls.total.setValue(Number(control.value.name.price) * Number(qw));
    control.controls.total.enable();
    this._calculateGrandTotal();
  }

  _resetControl(control: any) {
    control.controls.price.setValue(0.0);
    control.controls.total.setValue(0.0);
    control.controls.qwt.setValue(0);
    control.controls.total.disable();
    this._calculateGrandTotal();
  }

  calculateTotal(newTotalVal: number, control: any) {
    control.total.setValue(newTotalVal);
    this._calculateGrandTotal();
  }

  removeItemDetils(i: number, control: any) {
    this.t.removeAt(i);
    this._calculateGrandTotal();
  }

  _calculateGrandTotal() {
    let total = 0.0;
    if (this.t.controls) {
      this.t.controls.forEach((c: any) => {
        total += Number(c.controls.total.value);
      });
    }
    this.grandTotal = total;
  }
}
