import { ActivatedRoute } from '@angular/router';
import { UserService } from './../../user.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserModel, RoleApi, RoleType, dbRoleTypes, UIRoleTypes  } from '../../user.types';
import { numberPattern } from '@shared/patterns.constants';

@Component({
    selector: 'app-user',
    templateUrl: './add-user.component.html',
    styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {
    
    constructor(private fb: FormBuilder, private _service: UserService, private route: ActivatedRoute) { 
       this._getAllRoles();
    }

    addUserForm: FormGroup;
    addressForm: FormGroup;
    otherDetailsForm: FormGroup;
    
    allRoles:RoleType[];
    title = "Add";
    isLoading = false;
    languages = [
        {"name":'English', "value":"us_EN"},
        {"name":'Hindi', "value":"he_IN"},
    ]

    ngOnInit(): void {
        let isEdit = this.route.snapshot.paramMap.get('isEdit');
        let user = this._service.getSelectedUser();
        if(isEdit) {
            this.title = "Edit";
            this._createForm(user);
        } else {
            this._createForm({});
        }
    }

    _createForm(user:any) {

         this.addressForm = this.fb.group({
            "location": [ user.address && user.address.location || '', { validators: [Validators.required]}],
            "pincode": [ user.address && user.address.pincode || '' , { validators: [Validators.required]}],
            "state": [{value:'MP',disabled: true}, { validators: [Validators.required]}],
            "city": [{value:'Indore',disabled: true}, { validators: [Validators.required]}],
            "country":[{value:'India',disabled: true}, { validators: [Validators.required]}],
            "fullAddress":[ user.address && user.address.fullAddress || '' , { validators: [Validators.required]}]
        });
        
        
       
        this.otherDetailsForm = this.fb.group({
            "roleids": [user && user.roles && this._getRoleNames(user.roles) || '', { validators: [Validators.required]}],
            "language": [ user && user.userPreference && user.userPreference.language || '' , { validators: [Validators.required]}]
        });

        this.addUserForm = this.fb.group({
            firstName: [ user && user.firstName || '', { validators: [Validators.required]}],
            lastName: [ user && user.lastName || '', { validators: [Validators.required]}],
            whatsappNumber: [ user && user.whatsappNumber || '', { validators: [Validators.required]}],
            mobile: [ user && user.mobile || '', { validators: [Validators.required]}],
        });
    }

    get firstName() { return this.addUserForm.get('firstName'); }
    get lastName() { return this.addUserForm.get('lastName'); }
    get whatsappNumber() { return this.addUserForm.get('whatsappNumber'); }
    get mobile() { return this.addUserForm.get('mobile'); }
    get location() { return this.addressForm.get('location'); }
    get pincode() { return this.addressForm.get('pincode'); }
    get state() { return this.addressForm.get('state'); }
    get city() { return this.addressForm.get('city'); }
    get country() { return this.addressForm.get('country'); }
    get fullAddress() { return this.addressForm.get('fullAddress'); }
    get language() { return this.otherDetailsForm.get('language');  }
    get roleids() { return this.otherDetailsForm.get('roleids');  }
    
    
    _getAllRoles() {
        this._service.getAllRoles()
        .subscribe((res: RoleApi) => this.allRoles = res.items);
    }

    _getRoleNames(roles: RoleType[]) {
        if(roles && roles.length > 0) {
            return roles.map(role => UIRoleTypes[role.roleName]);
        }
    }
    

    _getRoleByName(roleName: string) {
        if(this.allRoles) {
            return this.allRoles.find((item: RoleType) => item.roleName === roleName);
        }
    }

    private _getRoleIdsByName(roleIds:any) {
        let roles:any[] = [];
        roleIds.forEach((name:string) => {
            let role = this._getRoleByName(dbRoleTypes[name]);
            roles.push({ id: role.id });
        });
        return roles;
    }

   
    onUserAdd(personalInfoform:any,userPreference: any, addressForm:any, stepper: any) {
        // console.log("user form =>", personalInfoform,addressForm,userPreference);
        let roles = this._getRoleIdsByName(userPreference.roleids);
        delete userPreference.roleids;
        this.isLoading = true;
        this._service.create({ ...personalInfoform, userPreference, address: addressForm, roles})
        .subscribe((res : UserModel)=> {
            this.isLoading = false;
            // this._createForm();
            stepper.reset();
        });
        
    }
}
