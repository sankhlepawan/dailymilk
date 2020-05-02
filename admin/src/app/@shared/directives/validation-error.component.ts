import { Directive, Input, ElementRef, Renderer2, OnInit,HostListener  } from '@angular/core';

@Directive({
   selector: '[appValidationError]'
})

export class ValidationErrorDirective {

   @Input("fieldVal") fieldVal:any;
   constructor(
      private renderer: Renderer2,
      private elmRef: ElementRef
   ) { }

   @HostListener('keydown', ['$event'])
   onKeyDown(e: KeyboardEvent) {
      console.log(e);
  if (
    // Allow: Delete, Backspace, Tab, Escape, Enter, etc
    (e.key === 'a' && e.ctrlKey === true) || // Allow: Ctrl+A
    (e.key === 'c' && e.ctrlKey === true) || // Allow: Ctrl+C
    (e.key === 'v' && e.ctrlKey === true) || // Allow: Ctrl+V
    (e.key === 'x' && e.ctrlKey === true) || // Allow: Ctrl+X
    (e.key === 'a' && e.metaKey === true) || // Cmd+A (Mac)
    (e.key === 'c' && e.metaKey === true) || // Cmd+C (Mac)
    (e.key === 'v' && e.metaKey === true) || // Cmd+V (Mac)
    (e.key === 'x' && e.metaKey === true) // Cmd+X (Mac)
  ) {
    return;  // let it happen, don't do anything
  }
  // Ensure that it is a number and stop the keypress
  if (e.key === ' ' || isNaN(Number(e.key))) {
    e.preventDefault();
  }
}

   // ngOnInit() {
   //    console.log(this.fieldVal);
   //     let msg =  this.fieldVal.hasError('required') ? 'this Field is Required.' :
   //     this.fieldVal.hasError('email') ? 'Not a valid email. ' :
   //     this.fieldVal.hasError('minlength') ? 'Minimum lenght required. ' :
   //     this.fieldVal.hasError('maxlength') ? 'Char can not be more than max lenght.' :
   //     this.fieldVal.hasError('passwordMismatch') ? 'Password didt not match' : '';

   //    const font = this.renderer.createElement('font');
   //    const text = this.renderer.createText(msg);
   //    this.renderer.appendChild(font, text);
   //    this.renderer.addClass(this.elmRef.nativeElement, 'val-error');
   //    this.renderer.appendChild(this.elmRef.nativeElement, font);
   // }

}


// import {Injectable} from '@angular/core';

// @Injectable()

// export class VlidationErrorMessageService {
  
//     constructor() {}

//   getFormErrorMessage(field) {
//     return field.hasError('required') ? 'this Field is Required.' :
//     field.hasError('email') ? 'Not a valid email. ' :
//     field.hasError('minlength') ? 'Minimum lenght required. ' :
//     field.hasError('maxlength') ? 'Char can not be more than max lenght.' :
//     field.hasError('passwordMismatch') ? 'Password didt not match' : '';
//   }

// }