import express from "express";
import { BaseController } from "./abstractions/base-controller";
import callService from "../utils/call-service";
import OrganizationNotFoundException from "../exceptions/organization-not-found-exception";
import { Prisma } from "@prisma/client";
import { User } from "../types/user.type";

export default class OrganizationController extends BaseController {
  public path = "/organization";

  constructor() {
    super();
    this.initializeRoutes();
  }

  public initializeRoutes() {
    this.router.get(this.path, this.getAllOrganization);
    this.router.post(this.path, this.addOrganization);
    this.router.get(this.path + "/find", this.findOrganization);
    this.router.get(this.path + "/:organizationID", this.getOrganizationById);
    this.router.put(this.path + "/:organizationID", this.updateOrganization);
    this.router.delete(this.path + "/:organizationID", this.deleteOrganization);
  }

  getOrganizationById = async (
    request: express.Request,
    response: express.Response,
    next: express.NextFunction,
  ) => {
    const organizationId = Number.parseInt(request.params.organizationID);
    const organization = await this.prisma.organization.findUnique({
      where: {
        id: organizationId,
      },
    });

    if (!organization) return next(new OrganizationNotFoundException());

    const user: User = await callService(
      "user-service",
      `api/users/${organization?.userID}`,
      "GET",
    );
    const res = {
      ...organization,
      name: user.name,
      email: user.email,
      phoneNumber: user.phoneNumber,
      avatar: user.avatar,
    };

    response.json(res);
  };

  findOrganization = async (
    request: express.Request,
    response: express.Response,
    next: express.NextFunction,
  ) => {
    const userId = request.query.userId as string;
    const organization = await this.prisma.organization.findUnique({
      where: {
        userID: userId,
      },
    });

    if (!organization) return next(new OrganizationNotFoundException());

    const user: User = await callService(
      "user-service",
      `api/users/${organization?.userID}`,
      "GET",
    );
    const res = {
      ...organization,
      name: user.name,
      email: user.email,
      phoneNumber: user.phoneNumber,
      avatar: user.avatar,
    };

    response.json(res);
  };

  getAllOrganization = async (
    request: express.Request,
    response: express.Response,
  ) => {
    const organization = await this.prisma.organization.findMany();

    response.json(organization);
  };

  addOrganization = async (
    request: express.Request,
    response: express.Response,
  ) => {
    const reqBody: Prisma.OrganizationCreateInput = request.body;
    console.table(request.body);
    const user = await callService(
      "user-service",
      `api/users/${reqBody.userID}`,
      "GET",
    );

    if (!user) {
      response.status(400).json({
        message:
          "Tạo tổ chức thất bại. Không tìm thấy thông tin user với userID = " +
          reqBody.userID,
      });
      return;
    }

    const organization = await this.prisma.organization.findUnique({
      where: {
        userID: reqBody.userID,
      },
    });

    if (organization) {
      response.status(400).json({
        message: "Mỗi user chỉ có thể quản lý một tổ chức" + reqBody.userID,
      });
      return;
    }

    const newOrganization = await this.prisma.organization.create({
      data: reqBody,
    });

    response.status(201).json(newOrganization);
  };

  updateOrganization = async (
    request: express.Request,
    response: express.Response,
  ) => {
    const organizationId = Number.parseInt(request.params.organizationID);
    const reqBody: Prisma.OrganizationUpdateInput = request.body;
    const count = await this.prisma.organization.update({
      where: {
        id: organizationId,
      },
      data: reqBody,
    });
    response.json(count);
  };

  deleteOrganization = async (
    request: express.Request,
    response: express.Response,
  ) => {
    const organizationId = Number.parseInt(request.params.organizationID);
    const count = await this.prisma.organization.delete({
      where: {
        id: organizationId,
      },
    });
    response.json(count);
  };
}
