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

export function NotEqualZero(controlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];

        // set error on matchingControl if validation fails
        if (control.value === 0) {
            control.setErrors({ notEqualZero: true });
        } else {
            control.setErrors(null);
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

export function ValidateSellAmount(controlName: string, controlNameCompare: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];
        const controlCompare = formGroup.controls[controlNameCompare];

        // set error on matchingControl if validation fails

        if (control.value > 0 && control.value >= controlCompare.value) {
            control.setErrors({ mustLessThan: true });
        } else if (control.value === 0) {
            control.setErrors({ notEqualZero: true });
        } else {
            control.setErrors(null);
        }
    }
}

export function formatDate(myDate: Date): string {
    if(!myDate){
        return null;
    }
    var monthNames = [
        "01", "02", "03",
        "04", "05", "06", "07",
        "08", "09", "10",
        "11", "12"
    ];
    var day = myDate.getDate();
    var monthIndex = myDate.getMonth();
    var year = myDate.getFullYear();

    return monthNames[monthIndex] + '/' + day + '/' + year;
}