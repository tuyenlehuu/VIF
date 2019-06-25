import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/User.model';
import { config } from '../config/application.config';
import { Pager } from '../models/Pager';
import { TokenResetPass } from '../models/TokenResetPass';
import { map } from 'rxjs/operators';

@Injectable()
export class ChangeInfoService {
  constructor(private http: HttpClient) { }
  getById(id: number) {
    return this.http.get(`${config.apiUrl}/change-info/${id}`);
  }
  update(user: User) {
    return this.http.put(`${config.apiUrl}/change-info/update`, user);
  }
  upFileAvatar(file: FormData) {
    return this.http.post(`${config.apiUrl}/change-info/upFileAvatar`, file, { responseType: 'text' });
  }
}