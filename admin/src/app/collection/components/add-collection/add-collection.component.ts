import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { CollectionModel, TestModeType, animalTypes } from '../../collection.types';
import { CollectionService } from '../../collection.service';
import { numberPattern } from '@shared/constants';

@Component({
  selector: 'app-add-collection',
  templateUrl: './add-collection.component.html',
  styleUrls: ['./add-collection.component.scss'],
})
export class AddCollectionComponent implements OnInit {
  constructor(private fb: FormBuilder, private _service: CollectionService) {
    this._createForm();
    this.itemDetailsType = [];
    this.itemDetailsType[0] = [];
    this.selectedItemDetails = [];
  }

  ngOnInit(): void {}

  addCollectionForm: FormGroup;
  itemDetailsForm: FormGroup;
  isLoading = false;
  categories: any;
  subCategoriesList: any[];
  itemDetailsType: any[];
  selectedCategoryId: number;
  selectedCategory: any;
  selectedItemDetails: string[];
  animalTypes: any = animalTypes;

  _createForm() {
    console.log(this.animalTypes);
    this.addCollectionForm = this.fb.group({
      type: ['', { validators: [Validators.required] }],
      testMode: [{ value: TestModeType.MANUAL, disabled: true }, { validators: [Validators.required] }],
      fat: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      snf: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      qwt: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      rate: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      amount: ['', { validators: [Validators.required, Validators.pattern(numberPattern)] }],
      density: ['', { validators: [Validators.pattern(numberPattern)] }],
      lactose: ['', { validators: [Validators.pattern(numberPattern)] }],
      salts: ['', { validators: [Validators.pattern(numberPattern)] }],
      protein: ['', { validators: [Validators.pattern(numberPattern)] }],
      temp: ['', { validators: [Validators.pattern(numberPattern)] }],
      water: ['', { validators: [Validators.pattern(numberPattern)] }],
    });
  }

  get lactose() {
    return this.addCollectionForm.get('lactose');
  }
  get salts() {
    return this.addCollectionForm.get('salts');
  }
  get protein() {
    return this.addCollectionForm.get('protein');
  }

  get temp() {
    return this.addCollectionForm.get('temp');
  }

  get water() {
    return this.addCollectionForm.get('water');
  }

  get type() {
    return this.addCollectionForm.get('type');
  }
  get testMode() {
    return this.addCollectionForm.get('testMode');
  }
  get fat() {
    return this.addCollectionForm.get('fat');
  }

  get snf() {
    return this.addCollectionForm.get('snf');
  }

  get qwt() {
    return this.addCollectionForm.get('qwt');
  }

  get rate() {
    return this.addCollectionForm.get('rate');
  }

  get amount() {
    return this.addCollectionForm.get('amount');
  }

  get density() {
    return this.addCollectionForm.get('density');
  }

  onCollectionAdd(values: CollectionModel) {
    this.isLoading = true;
    console.log(values);
    values.testMode = TestModeType.MANUAL;
    this._service.addCollection(values).subscribe((res) => {
      this.isLoading = false;
      this.addCollectionForm.markAsPristine();
      this._createForm();
    });
  }
}
