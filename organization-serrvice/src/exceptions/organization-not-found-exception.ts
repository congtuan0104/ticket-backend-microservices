import HttpException from "./http-exception";

class OrganizationNotFoundException extends HttpException {
  constructor() {
    super(404, `Không tìm thấy tổ chức`);
  }
}

export default OrganizationNotFoundException;
