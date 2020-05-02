import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialModule } from '@app/material.module';
import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { SharedModule } from '@shared';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { UserFilterComponent, AddUserComponent } from './components';

const components = [ UserComponent,UserFilterComponent, AddUserComponent ];
const modules = [CommonModule, TranslateModule, FlexLayoutModule, MaterialModule, UserRoutingModule, SharedModule,FormsModule, ReactiveFormsModule]
@NgModule({
  imports: [...modules],
  declarations: [...components],
})
export class UserModule {}
