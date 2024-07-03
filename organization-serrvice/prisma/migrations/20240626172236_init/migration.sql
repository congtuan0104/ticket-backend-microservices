-- CreateTable
CREATE TABLE "Organization" (
    "id" SERIAL NOT NULL,
    "userID" TEXT NOT NULL,
    "description" TEXT NOT NULL,

    CONSTRAINT "Organization_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "Organization_userID_key" ON "Organization"("userID");
