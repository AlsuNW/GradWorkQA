FROM node:erbium-alpine3.12
WORKDIR /opt1/app
COPY /gate-simulator/app.js .
COPY /gate-simulator/data.json .
COPY /gate-simulator/package-lock.json .
COPY /gate-simulator/package.json .
RUN npm install
CMD ["npm", "start"]
EXPOSE 9999