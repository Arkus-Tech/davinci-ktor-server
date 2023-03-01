package io.davinci.utils

fun String.wrapWithProjectBoardFilter(): String {
  return "I want to start a new project. I'm going to make a new Trello board with tasks to complete my project. " +
      "Generate a name for my project. " +
      "Take in the description of my project and generate a JSON that matches the following format:\n\ntype Project {\n  " +
      "projectName: string,\n  ticketList: List of Ticket,\n}\ntype Ticket {\n  title: string,\n  scope: string,\n  " +
      "acceptance criteria: List of string // one string per criteria\n  helpfulResources: List of string\n}" +
      "\n\nRespond in string that's soul purpose is that it can be parsed into json object (including having keys be " +
      "wrapped in double quotes and don't forget to use backslashes to include certain characters in the string and don't put any unexpected end-of-input in field name).\n\n" +
      "Please help me see my project to the finish line! Here's a description of my project:\n\n$this."
}
