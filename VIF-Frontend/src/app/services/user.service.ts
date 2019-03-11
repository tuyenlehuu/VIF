import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User.model';
import { config } from '../config/application.config';

@Injectable()
export class UserService{
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`${config.apiUrl}/user/getAlls`);
    }

    getById(id: number) {
        return this.http.get(`${config.apiUrl}/user/${id}`);
    }

    register(user: User) {
        return this.http.post(`${config.apiUrl}/user/add`, user);
    }

    update(user: User) {
        return this.http.put(`${config.apiUrl}/user/update`, user); 
    }

    deleteById(id: number) {
        return this.http.delete(`${config.apiUrl}/user/deleteById/${id}`);
    }

    deleteByUsername(username: string) {
        return this.http.delete(`${config.apiUrl}/user/deleteByName/${username}`);
    }
}