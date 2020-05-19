import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { Angulartics2Module } from 'angulartics2';
import { SharedModule } from '@shared';
import { MaterialModule } from '@app/material.module';
import { CollectionRoutingModule } from './collection-routing.module';
import { CollectionComponent } from './collection.component';
import { AddCollectionComponent } from './components';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TranslateModule,
    SharedModule,
    FlexLayoutModule,
    MaterialModule,
    Angulartics2Module,
    CollectionRoutingModule,
    ReactiveFormsModule,
  ],
  declarations: [CollectionComponent, AddCollectionComponent],
})
export class CollectionModule {}
