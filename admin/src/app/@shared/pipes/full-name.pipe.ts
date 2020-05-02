import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'fullName', pure: true })
export class FullNamePipe implements PipeTransform {
  transform(value: any): any {
    if (value) {
      let name = '';
      if (value.firstName) {
        name += value.firstName + ' ';
      }
      if (value.lastName) {
        name += value.lastName;
      }
      if (!name) {
        name = 'NA';
      }
      return name;
    }
  }
}
