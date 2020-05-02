import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Angulartics2Module } from 'angulartics2';
import { SharedModule } from '@shared';
import { MaterialModule } from '@app/material.module';
import { ProductRoutingModule  } from './product-routing.module';
import { ProductComponent } from './product.component';
import { AddProductComponent } from './components';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    SharedModule,
    FlexLayoutModule,
    MaterialModule,
    Angulartics2Module,
    ProductRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ ProductComponent, AddProductComponent ],
})
export class ProductModule {}
