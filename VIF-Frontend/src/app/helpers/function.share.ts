import { FormGroup } from '@angular/forms';

// export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
//     return (control: AbstractControl): { [key: string]: any } | null => {
//         const forbidden = nameRe.test(control.value);
//         return forbidden ? { 'forbiddenName': { value: control.value } } : null;
//     };
// }

export function MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (matchingControl.errors && !matchingControl.errors.mustMatch) {
            // return if another validator has already found an error on the matchingControl
            return;
        }

        // set error on matchingControl if validation fails
        if (control.value !== matchingControl.value) {
            matchingControl.setErrors({ mustMatch: true });
        } else {
            matchingControl.setErrors(null);
        }
    }
}

export function RequireCombo(controlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];

        // set error on matchingControl if validation fails
        if (control.value == -1) {
            control.setErrors({ requireCombo: true });
        } else {
            control.setErrors(null);
        }
    }
}