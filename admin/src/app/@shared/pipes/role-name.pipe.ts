import { Pipe, PipeTransform } from '@angular/core';
import {  roleTypes } from './types' 

@Pipe({
    name: 'roleName',
    pure: true
})
export class RoleNamePipe implements PipeTransform {
    
    transform(roles: any): any {
        if(roles) {
            if(Array.isArray(roles)) {
                return roles.map((role:any) => {
                   return roleTypes[role.roleName];
                }).join(",");
            } else if(roleTypes[roles]){
                return roleTypes[roles] ;
            }
            return roles
            
        }
    }
}